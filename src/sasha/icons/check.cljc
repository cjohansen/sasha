(ns sasha.icons.check
  "https://phosphoricons.com/"
  (:require [sasha.components.icon :as icon]))

(defn check [& [params]]
  [:svg (icon/params (assoc params :viewBox "0 0 256 256"))
   [:polyline
    {:points "216 72 104 184 48 128"
     :fill "none"
     :stroke "currentColor"
     :stroke-linecap "round"
     :stroke-linejoin "round"
     :stroke-width "16"}]])
