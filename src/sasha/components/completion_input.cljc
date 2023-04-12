(ns sasha.components.completion-input
  (:require [dumdom.core :refer [defcomponent]]
            [dumdom.element :refer [event-handler]]))

(defcomponent CompletionInput [data]
  (let [complete-handler (event-handler (:onComplete data))
        submit-handler (event-handler (:onSubmit data))]
    [:div {:style {:position "relative"}}
     [:input.input.suggestion {:value (or (:completion data) "")}]
     [:input.input
      (assoc data
             :style {:position "absolute"
                     :top 0
                     :bottom 0
                     :left 0
                     :right 0
                     :background "transparent"}
             :onClick (fn [e]
                        (when (and (not-empty (:value data))
                                   (:completion data)
                                   complete-handler)
                          (complete-handler e)))
             :onKeyDown (fn [e]
                          (when (and (= "Enter" (.-key e))
                                     submit-handler)
                            (.preventDefault e)
                            (submit-handler e))
                          (when (and (#{"Tab" "ArrowRight"} (.-key e))
                                     complete-handler)
                            (.preventDefault e)
                            (complete-handler e))))]]))
