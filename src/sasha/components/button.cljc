(ns sasha.components.button
  (:require [sasha.components.spinner :refer [Spinner]]))

(defn Button [{:keys [id text type href disabled?] :as props}]
  [(if (#{"button" "submit"} type) :button :a)
   (merge
    {:className (str "button text-m"
                     (when disabled? " button-disabled"))}
    (when type {:type type})
    (when (and type disabled?) {:disabled "true"})
    (when id {:id id})
    (when href {:href href}))
   (when (:spinner? props)
     (Spinner))
   text])
