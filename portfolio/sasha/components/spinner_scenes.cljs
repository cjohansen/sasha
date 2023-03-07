(ns sasha.components.spinner-scenes
  (:require [portfolio.dumdom :refer-macros [defscene defns]]
            [sasha.components.spinner :refer [Spinner]]))

(defns Spinner
  :group :components)

(defscene spinner
  [:div {:style {:max-width 100}}
   (Spinner)])
