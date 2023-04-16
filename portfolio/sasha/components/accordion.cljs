(ns sasha.components.accordion
  (:require [dumdom.core :refer-macros [defcomponent]]
            [sasha.components.elastic-container :as ec]
            [sasha.icons :as icons]))

(defcomponent BellowBody
  :will-enter (ec/enter)
  :will-leave (ec/leave)
  [{:keys [text]}]
  [:div
   [:div {:style {:padding "12px 48px 24px 24px"}}
    text]])

(defcomponent Bellow
  [{:keys [title toggle content actions on-click]}]
  [:div.bellow {:style {:border-bottom "1px solid var(--bellow-border)"}}
   [:div.row
    {:on-click (or actions on-click)
     :style {:justify-content "space-between"
             :padding 12
             :cursor "pointer"}}
    title
    (when-let [{:keys [icon rotation]} toggle]
      (icons/render
       icon
       {:size 16
        :style {:transition "transform 0.15s ease-in"
                :transform (str "rotate(" (or rotation 0) "deg)")}}))]
   (when content
     (BellowBody content))])

(defcomponent Accordion
  [{:keys [items]}]
  [:div
   (map Bellow items)])
