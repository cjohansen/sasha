(ns sasha.components.icon)

(defn params [params]
  (assoc params :style (into {:color (:color params)
                              :width (:size params)
                              :height (:size params)}
                             (:style params))))
