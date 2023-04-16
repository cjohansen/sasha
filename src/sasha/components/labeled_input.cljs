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
