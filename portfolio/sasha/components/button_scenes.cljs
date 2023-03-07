(ns sasha.components.button-scenes
  (:require [portfolio.dumdom :refer-macros [defscene defns]]
            [sasha.components.button :refer [Button]]))

(defns Button
  :group :components)

(defscene button
  (Button {:text "Click it"
           :href "/lul"}))

(defscene disabled-button
  (Button {:text "Click it"
           :href "/lul"
           :disabled? true}))

(defscene button-with-spinner
  (Button {:text "Click it"
           :href "/lul"
           :disabled? true
           :spinner? true}))
