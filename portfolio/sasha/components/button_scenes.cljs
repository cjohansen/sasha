(ns sasha.components.button-scenes
  (:require [portfolio.dumdom :refer-macros [defscene]]
            [sasha.components.button :refer [Button]]))

(defscene button
  (Button {:text "Click it"
           :href "#"}))

(defscene disabled-button
  (Button {:text "Click it"
           :disabled? true}))

(defscene button-with-spinner
  (Button {:text "Click it"
           :disabled? true
           :spinner? true}))
