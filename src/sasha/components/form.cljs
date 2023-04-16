(ns sasha.components.form
  (:require [dumdom.core :refer-macros [defcomponent]]
            [sasha.components.button :refer [Button]]
            [sasha.components.labeled-input :refer [LabeledInput]]
            [sasha.icons :as icons]))

(defcomponent Form [{:keys [fields button actions]}]
  [:form.form {:on-submit actions}
   (for [field fields]
     [:div.line (LabeledInput field)])
   (when button
     [:div.line
      (Button button)])])

(defcomponent Receipt [{:keys [title icon text fields]}]
  [:div.article
   [:h2.h3.mod
    title
    (when icon
      (icons/render icon {:size 24 :style {:margin-left 8}}))]
   [:p.mod text]
   [:ul.mod
    (for [{:keys [label value]} fields]
      [:li [:strong label ":"] " " value])]])
