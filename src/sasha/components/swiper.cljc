(ns sasha.components.swiper
  (:require [dumdom.core :refer [defcomponent]]
            [dumdom.element :refer [event-handler]]
            [clojure.string :as str]))

(defn- touch-x [e]
  (some-> e .-changedTouches (aget 0) .-screenX))

(defn- parse-num [v]
  #?(:cljs
     (let [num (js/parseInt v 10)]
       (if (js/isNaN num)
         0
         num))
     :clj
     (try
       (Integer/parseInt v)
       (catch Exception e
         nil))))

(defn- set-left [el pos]
  (set! (.-left (.-style el)) (str pos "px")))

(defn- animate-left-pos [el pos & [duration]]
  (let [duration (or duration 150)]
    (set! (.-transition (.-style el)) (str "left " duration "ms"))
    #?(:cljs
       (do
         (js/setTimeout #(set-left el pos))
         (js/setTimeout #(set! (.-transition (.-style el)) "") duration))
       :clj
       (do
         (set-left el pos)
         (set! (.-transition (.-style el)) "")))))

(defn get-threshold [w t]
  (let [t (if (number? t) [t] t)]
    (when (coll? t)
      (->> (cycle t)
           (take 2)
           (map #(cond-> %
                   (<= (abs %) 1) (* w)))))))

(defn fire-threshold-events [pos props target-x left-el right-el]
  (let [{:keys [past-left-in-threshold?
                past-left-out-threshold?
                past-right-in-threshold?
                past-right-out-threshold?]} @pos
        {:keys [left-threshold right-threshold]} props]
    (when (<= 0 target-x)
      (when-let [[in-t out-t] left-threshold]
        (when (or (and (not past-left-in-threshold?)
                       (<= in-t target-x))
                  (and (not past-left-out-threshold?)
                       (<= out-t target-x)))
          (swap! pos assoc :past-left-in-threshold? true)
          (some-> left-el .-classList (.add "swiper-above-threshold"))
          (when-let [f (:on-above-left-threshold props)]
            (f)))

        (when (and (not past-left-out-threshold?)
                   (<= out-t target-x))
          (swap! pos assoc :past-left-out-threshold? true))

        (when (or (and past-left-in-threshold?
                       (< target-x in-t))
                  (and past-left-out-threshold?
                       (< target-x out-t)))
          (swap! pos assoc :past-left-out-threshold? false)
          (some-> left-el .-classList (.remove "swiper-above-threshold"))
          (when-let [f (:on-below-left-threshold props)]
            (f)))

        (when (and past-left-in-threshold?
                   (<= target-x in-t))
          (swap! pos assoc :past-left-in-threshold? false))))

    (when (<= target-x 0)
      (when-let [[in-t out-t] right-threshold]
        (let [target-x (- target-x)]
          (when (or (and (not past-right-in-threshold?)
                         (<= in-t target-x))
                    (and (not past-right-out-threshold?)
                         (<= out-t target-x)))
            (swap! pos assoc :past-right-in-threshold? true)
            (some-> right-el .-classList (.add "swiper-above-threshold"))
            (when-let [f (:on-above-right-threshold props)]
              (f)))

          (when (and (not past-right-out-threshold?)
                     (<= out-t target-x))
            (swap! pos assoc :past-right-out-threshold? true))

          (when (or (and past-right-in-threshold?
                         (< target-x in-t))
                    (and past-right-out-threshold?
                         (< target-x out-t)))
            (swap! pos assoc :past-right-out-threshold? false)
            (some-> right-el .-classList (.remove "swiper-above-threshold"))
            (when-let [f (:on-below-right-threshold props)]
              (f)))

          (when (and past-right-in-threshold?
                     (<= target-x in-t))
            (swap! pos assoc :past-right-in-threshold? false)))))))

(defn with-defaults [props swipee left-el right-el]
  (let [swipee-width (some-> swipee .-clientWidth)]
    {:swipe-threshold (or (:swipe-threshold props) 5)
     :left-threshold (get-threshold swipee-width (or (:left-threshold props) (some-> left-el .-clientWidth)))
     :right-threshold (get-threshold swipee-width (or (:right-threshold props) (some-> right-el .-clientWidth)))
     :snap-left (or (:snap-left props) 0.25)
     :snap-right (or (:snap-right props) 0.25)
     :on-above-left-threshold (event-handler (:on-above-left-threshold props))
     :on-below-left-threshold (event-handler (:on-below-left-threshold props))
     :on-above-right-threshold (event-handler (:on-above-right-threshold props))
     :on-below-right-threshold (event-handler (:on-below-right-threshold props))
     :on-snap-left (event-handler (:on-snap-left props))
     :on-snap-right (event-handler (:on-snap-right props))}))

(defn get-target-direction [left-offset direction left-el right-el]
  (cond
    (< left-offset 0) :right
    (< 0 left-offset) :left

    (and (= :left direction)
         right-el)
    :right

    (and (= :right direction)
         left-el)
    :left))

(defn get-direction-update [{:keys [direction x]} event-x]
  (when-not direction
    (if (< event-x x)
      :left
      :right)))

(defn mount-swiper [node props]
  #?(:cljs
     (let [left-el (some-> node (.querySelector ".swipe-left"))
           right-el (some-> node (.querySelector ".swipe-right"))
           swipee (some-> node (.querySelector ".swipee"))
           props (with-defaults props swipee left-el right-el)
           pos (atom {:x 0 :left 0})
           touchstart (fn [e]
                        (reset! pos {:x (touch-x e)
                                     :left (parse-num (.-left (.-style swipee)))
                                     :left-threshold (first (:left-threshold props))
                                     :right-threshold (first (:right-threshold props))
                                     :past-left-threshold? (:past-left-threshold? @pos)
                                     :past-right-threshold? (:past-right-threshold? @pos)}))
           touchmove
           (fn [e]
             (when-let [event-x (touch-x e)]
               (let [direction-update (get-direction-update @pos event-x)
                     {:keys [x left direction]} @pos
                     direction (or direction-update direction)
                     left-pos (+ left (- event-x x))
                     target-direction (get-target-direction left direction left-el right-el)]
                 (when direction-update
                   (swap! pos assoc :direction direction-update)
                   (let [[hide show] (if (= :left target-direction)
                                       [right-el left-el]
                                       [left-el right-el])]
                     (when hide
                       (when-not (.-originalDisplay hide)
                         (set! (.-originalDisplay hide)
                               (.getPropertyValue (js/getComputedStyle hide) "display")))
                       (set! (.. hide -style -display) "none"))
                     (when (and show (.-originalDisplay show))
                       (set! (.. show -style -display)
                             (.-originalDisplay show)))))
                 (when (or (not= 0 left)
                           (< (:swipe-threshold props) (abs (- left left-pos))))
                   (.preventDefault e)
                   (let [new-left (case target-direction
                                    :left (js/Math.max left-pos 0)
                                    :right (js/Math.min left-pos 0)
                                    0)]
                     (fire-threshold-events pos props new-left left-el right-el)
                     (set-left swipee new-left))))))
           touchend (fn [e]
                      (let [{:keys [left direction]} @pos
                            start-x left
                            right-width (some-> right-el .-clientWidth)
                            left-width (some-> left-el .-clientWidth)
                            diff (- (parse-num (.-left (.-style swipee))) start-x)
                            new-left (cond
                                       (and right-width
                                            (<= start-x 0)
                                            (< diff 0)
                                            (< (* right-width (:snap-right props)) (abs diff)))
                                       (- right-width)

                                       (and left-width
                                            (<= 0 start-x)
                                            (< 0 diff)
                                            (< (* left-width (:snap-left props)) diff))
                                       left-width

                                       :default 0)]

                        (fire-threshold-events pos props new-left left-el right-el)
                        (animate-left-pos swipee new-left)

                        (when (and (= new-left (- right-width))
                                   (= direction :left)
                                   (:on-snap-right props))
                          ((:on-snap-right props)))
                        (when (and (= new-left left-width)
                                   (= direction :right)
                                   (:on-snap-left props))
                          ((:on-snap-left props)))))]
       (when swipee
         (set! (.. swipee -style -position) "relative")
         (set! (.. swipee -style -left) 0)
         (.addEventListener node "contextmenu" #(.preventDefault %) false)
         (.addEventListener node "touchstart" touchstart false)
         (.addEventListener node "mousedown" touchstart false)
         (.addEventListener node "touchmove" touchmove false)
         (.addEventListener node "mousemove" touchmove false)
         (.addEventListener node "touchend" touchend false)
         (.addEventListener node "mouseup" touchend false)))))

(defn swipable-container [& children]
  (apply vector
         :div
         {:style {:position "relative"
                  :overflow "hidden"}}
         children))

(defcomponent SwipeReveal
  :on-mount mount-swiper

  [{:keys [swipee right left]}]
  (swipable-container
   (when right
     [:div.swipe-right {:style {:position "absolute"
                                :right 0
                                :top 0
                                :bottom 0}}
      right])
   (when left
     [:div.swipe-left {:style {:position "absolute"
                               :left 0
                               :top 0
                               :bottom 0}}
      left])
   [:div.swipee swipee]))

(defcomponent SwipeControl [{:keys [color content align class-name]}]
  [:div.swipe-control
   {:className (->> [class-name
                     (if (= :right align)
                       "swipe-right"
                       "swipe-left")]
                    (remove empty?)
                    (str/join " "))
    :style {:background color
            :display "flex"
            :align-items "center"
            :justify-content (when (= :right align)
                               "flex-end")
            :padding "0 20px"
            :position "absolute"
            :right 0
            :left 0
            :top 0
            :bottom 0}}
   content])
