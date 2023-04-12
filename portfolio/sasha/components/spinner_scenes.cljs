(ns sasha.components.spinner-scenes
  (:require [portfolio.dumdom :refer-macros [defscene]]
            [sasha.components.spinner :refer [Spinner]]))

(defscene spinner
  [:div {:style {:max-width 100}}
   (Spinner)])
