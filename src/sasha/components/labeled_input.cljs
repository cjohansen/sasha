(ns sasha.components.labeled-input
  (:require [dumdom.core :refer [defcomponent]]
            [sasha.icons :as icons]))

(defcomponent LabeledInput [{:keys [id label value on-input error? messages disabled?]}]
  [:div.labeled-input
   {:class (when error? :error)}
   [:div.row
    [:label.li-label {:for id} label]
    [:input.input.li-input
     {:id id
      :value value
      :disabled disabled?
      :type "text"}]]
   (when (seq messages)
     [:div.li-addendums
      (for [{:keys [kind text icon]} messages]
        [:div.row {:class kind}
         (when icon
           (icons/render icon {:size 16
                               :style {:margin-right 8}}))
         text])])])

(defcomponent LabeledInput2 [{:keys [id label value on-input error? messages disabled? mobile?]}]
  [:div
   {:style {:color (when error? "var(--error)")}}
   [:div {:style {:display (when-not mobile? "flex")
                  :align-items "center"}}
    [:label {:for id
             :style (if mobile?
                      {:display "block"
                       :margin "0 0 10px"}
                      {:flex "0 0 30%"})}
     label]
    [:input.input
     {:id id
      :value value
      :disabled disabled?
      :type "text"
      :style {:background (when disabled? "var(--input-disabled-bg)")
              :color (when disabled? "var(--input-disabled-fg)")}}]]
   (when (seq messages)
     [:div {:style {:margin-left (when-not mobile? "30%")
                    :font-size "0.9rem"}}
      (for [{:keys [kind text icon]} messages]
        [:div {:class kind
               :style {:display "flex"
                       :align-items "center"
                       :margin "10px 0"}}
         (when icon
           (icons/render icon {:size 16
                               :style {:margin-right 8}}))
         text])])])
