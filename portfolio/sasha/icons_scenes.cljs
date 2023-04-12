(ns sasha.icons-scenes
  (:require [portfolio.dumdom :as portfolio :refer-macros [defscene]]
            [portfolio.data :as data]
            [sasha.icons :as icons]))

(data/register-collection!
 :icon-folder
 {:title "Icons"})

(portfolio/configure-scenes
 {:collection :icon-folder
  :title "Icons"})

(defscene icon-listing
  [:div {:style {:display "flex"
                 :gap 20
                 :flex-wrap "wrap"}}
   (for [icon (icons/get-icon-ids)]
     [:div {:style {:flex "0 0 32px"}
            :title (str icon)}
      (icons/render icon {:size 32})])])

(defscene colorized-icon-listing
  [:div {:style {:display "flex"
                 :gap 20
                 :color "red"
                 :flex-wrap "wrap"}}
   (for [icon (icons/get-icon-ids)]
     [:div {:style {:flex "0 0 32px"}
            :title (str icon)}
      (icons/render icon {:size 32})])])
