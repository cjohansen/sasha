(ns sasha.components.spinner)

(defn Spinner [& [attrs]]
  [:svg.spinner (merge attrs {:viewBox "0 0 100 100"})
   [:g.spinner-inner
    [:circle
     {:fill "none"
      :stroke-linecap "round"
      :stroke-width "10"
      :cx "50"
      :cy "50"
      :r "31"
      :stroke "var(--spinner-track)"}]
    [:circle.spinner-circle
     {:r "31"
      :stroke "var(--spinner-line)"
      :fill "none"
      :stroke-dashoffset "0"
      :stroke-width "10"
      :cx "50"
      :cy "50"
      :stroke-dasharray "200"
      :stroke-linecap "round"}]]])
