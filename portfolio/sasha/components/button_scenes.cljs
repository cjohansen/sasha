(ns sasha.components.button-scenes
  (:require [portfolio.reagent :refer-macros [defscene]]
            [sasha.components.button :refer [Button]]))

(defn form-1-component [{:keys [text] :as m}]
  [:button m text])

(defn form-2-component [_]
  (let [add-num #(str % (rand-int 100))]
    (fn [{:keys [text] :as m}]
      [:button m (add-num text)])))

(defscene form-2-button
  :params {:text "Click it"
           :href "#"}
  form-2-component)

(defscene form-1-disabled-button
  :params {:text "Click it"
           :disabled true}
  form-1-component)

(defscene form-1-button-with-text-added
  :params {:text (str "Click it" (rand-int 100))}
  form-1-component)
