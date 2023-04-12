(ns sasha.components.swiper-scenes
  (:require [dumdom.core :refer [defcomponent]]
            [portfolio.dumdom :refer-macros [defscene]]
            [sasha.components.swiper :as swiper :refer [SwipeControl SwipeReveal]]
            [sasha.icons.check :as check]
            [sasha.icons.trash :as trash]))

(def styles
  {:display "flex"
   :align-items "center"
   :padding "10px 20px"
   :height "50px"})

(defscene swip-control-right
  :param (atom {})
  [store]
  [:div {}
   [:p {:style {:margin-bottom 10}} "Click it to toggle state"]
   [:div {:style {:height 50
                  :position "relative"}
          :onClick (fn [e] (swap! store update :clicked? not))}
    (SwipeControl
     {:content (trash/trash {:color "#ffffff"
                             :size 24})
      :color "var(--mexican-rojo)"
      :align :right
      :class-name (when (:clicked? @store)
                    "swiper-above-threshold")})]])

(defscene swip-control-left
  :param (atom {})
  [store]
  [:div {}
   [:p {:style {:margin-bottom 10}} "Click it to toggle state"]
   [:div {:style {:height 50
                  :position "relative"}
          :onClick (fn [_e] (swap! store update :clicked? not))}
    (SwipeControl
     {:content (check/check {:color "#ffffff"
                             :size 24})
      :color "var(--lima)"
      :class-name (when (:clicked? @store)
                    "swiper-above-threshold")})]])

(defscene swipe-left
  (SwipeReveal
   {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
             "Drag me right"]
    :left [:div {:style (assoc styles :background "var(--lima)")} "OK!"]}))

(defscene swipe-right
  (SwipeReveal
   {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
             "Drag me left"]
    :right [:div {:style (assoc styles :background "var(--mexican-rojo)")} "NO!"]}))

(defscene swipe-both-ways
  (SwipeReveal
   {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
             "Drag me both ways"]
    :left [:div {:style (assoc styles :background "var(--lima)")} "OK!"]
    :right [:div {:style (assoc styles :background "var(--mexican-rojo)")} "NO!"]}))

(defscene swipe-with-left-threshold-transition
  :param (atom {})
  [store]
  (SwipeReveal
   {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
             "Dragging right yields a transition"]
    :left [:div {:style (assoc styles
                               :transition "background 0.5s"
                               :background (if (:ok? @store)
                                             "var(--lima)"
                                             "var(--mine-shaft)"))} "OK!"]
    :on-above-left-threshold (fn []
                               (swap! store assoc :ok? true))
    :on-below-left-threshold (fn []
                               (swap! store assoc :ok? false))}))

(defscene swipe-with-right-threshold-transition
  :param (atom {})
  [store]
  (SwipeReveal
   {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
             "Dragging left yields a transition"]
    :right [:div {:style (assoc styles
                                :transition "background 0.5s"
                                :background (if (:ok? @store)
                                              "var(--mexican-rojo)"
                                              "var(--mine-shaft)"))} "NO!"]
    :on-above-right-threshold (fn []
                                (swap! store assoc :ok? true))
    :on-below-right-threshold (fn []
                                (swap! store assoc :ok? false))}))

(defscene swipe-both-ways-with-transitions
  :param (atom {})
  [store]
  (SwipeReveal
   {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
             "Drag me both ways"]
    :left [:div {:style (assoc styles
                               :background "var(--lima)"
                               :padding-right 100
                               :transition "opacity 0.5s"
                               :opacity (if (:left? @store)
                                          1
                                          0.1))} "OK!"]
    :right [:div {:style (assoc styles
                                :background "var(--mexican-rojo)"
                                :padding-left 100
                                :transition "opacity 0.5s"
                                :opacity (if (:right? @store)
                                           1
                                           0.1))} "NO!"]
    :right-threshold 0.25
    :left-threshold 0.25
    :on-above-right-threshold #(swap! store assoc :right? true)
    :on-below-right-threshold #(swap! store assoc :right? false)
    :on-above-left-threshold #(swap! store assoc :left? true)
    :on-below-left-threshold #(swap! store assoc :left? false)}))

(defscene snap-left-disappear
  :param (atom {})
  [store]
  [:div
   (when (:retry? @store)
     [:a {:onClick (fn [e]
                     (.preventDefault e)
                     (swap! store dissoc :gone? :retry?))}
      "Try again"])
   (when-not (:gone? @store)
     [:div {:style {:height 50
                    :transition "height 0.5s"
                    :overflow "hidden"}
            :leaving-style {:height 0}}
      (SwipeReveal
       {:swipee [:div {:style (assoc styles :background "var(--contrast-bg)")}
                 "Drag me left"]
        :right [:div {:style (assoc styles
                                    :background "var(--mexican-rojo)"
                                    :opacity (if (:active? @store) 1 0.1)
                                    :padding-left 100)} "NO!"]
        :right-threshold 0.25
        :snap-right 0.4
        :on-above-right-threshold #(swap! store assoc :active? true)
        :on-below-right-threshold #(swap! store assoc :active? false)
        :on-snap-right (fn []
                         (swap! store assoc :gone? true)
                         (js/setTimeout #(swap! store assoc :retry? true :active? false) 1000))
        })])])

(defcomponent CustomSwipable
  :on-mount swiper/mount-swiper
  [data]
  (swiper/swipable-container
   [:div.swipe-left
    {:style {:background "var(--mexican-rojo)"
             :opacity (if (:active? data) 1 0.1)
             :transition (if (:active? data)
                           "opacity 0.5s ease-in"
                           "opacity 0.75s ease-out")
             :position "absolute"
             :right 0
             :left 0
             :top 0
             :bottom 0}}
    [:div {:style styles} "NO!"]]
   [:div.swipee
    [:div {:style (assoc styles :background "var(--contrast-bg)")}
     "Drag me right"]]))

(defscene custom-swipable
  :param (atom
          {:left-threshold [0.1 0.2]
           :snap-left 0.3})
  [store]
  [:div
   (when (:retry? @store)
     [:a {:onClick (fn [e]
                     (.preventDefault e)
                     (swap! store dissoc :gone? :retry?))}
      "Try again"])
   (when-not (:gone? @store)
     [:div {:style {:height 50
                    :transition "height 0.5s"
                    :overflow "hidden"}
            :leaving-style {:height 0}}
      (CustomSwipable
       (assoc @store
              :on-above-left-threshold #(swap! store assoc :active? true)
              :on-below-left-threshold #(swap! store assoc :active? false)
              :on-snap-left (fn []
                              (swap! store assoc :gone? true)
                              (js/setTimeout #(swap! store assoc :retry? true :active? false) 1000))
              ))])])

(defcomponent CustomSwipeRight
  :on-mount swiper/mount-swiper
  [data]
  (swiper/swipable-container
   [:div.swipe-right
    {:style {:background "var(--mexican-rojo)"
             :opacity (if (:active? data) 1 0.1)
             :transition (if (:active? data)
                           "opacity 0.5s ease-in"
                           "opacity 0.75s ease-out")
             :position "absolute"
             :right 0
             :left 0
             :top 0
             :bottom 0}}
    [:div {:style (assoc styles :justify-content "flex-end")} "NO!"]]
   [:div.swipee
    [:div {:style (assoc styles :background "var(--contrast-bg)")}
     "Drag me left"]]))

(defscene custom-swipable-other-side
  :param (atom
          {:right-threshold [0.1 0.5]
           :snap-right 0.3})
  [store]
  [:div
   (when (:retry? @store)
     [:a {:onClick (fn [e]
                     (.preventDefault e)
                     (swap! store dissoc :gone? :retry?))}
      "Try again"])
   (when-not (:gone? @store)
     [:div {:style {:height 50
                    :transition "height 0.5s"
                    :overflow "hidden"}
            :leaving-style {:height 0}}
      (CustomSwipeRight
       (assoc @store
              :on-above-right-threshold #(swap! store assoc :active? true)
              :on-below-right-threshold #(swap! store assoc :active? false)
              :on-snap-right (fn []
                               (swap! store assoc :gone? true)
                               (js/setTimeout #(swap! store assoc :retry? true :active? false) 1000))
              ))])])
