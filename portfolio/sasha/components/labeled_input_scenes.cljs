(ns sasha.components.labeled-input-scenes
  (:require [sasha.components.labeled-input :refer [LabeledInput LabeledInput2]]
            [portfolio.dumdom :refer-macros [defscene]]))

(defscene empty-input
  (LabeledInput
   {:id "name"
    :label "Your name"}))

(defscene input-with-text
  (LabeledInput
   {:id "email"
    :label "Your email"
    :value "christian@kodemaker.no"}))

(defscene input-with-warning
  (LabeledInput
   {:id "email-2"
    :label "Your email"
    :value "christian@kodemaker."
    :error? true
    :messages [{:kind :error
                :icon :sasha.icons/warning
                :text "Email seems incomplete"}]}))

(defscene disabled-input
  (LabeledInput
   {:id "email-3"
    :label "Your email"
    :value "christian@kodemaker.no"
    :disabled? true}))

(defn render-scene [data opt]
  (LabeledInput2
   (cond-> data
     (and (number? (:viewport/width opt))
          (< (:viewport/width opt) 600))
     (assoc :mobile? true))))

(defscene input2-with-text
  "`LabeledInput2` uses runtime data for responsive design instead
   of relying on CSS media-queries."
  :params {:id "email-4"
           :label "Your email"
           :value "christian@kodemaker.no"}
  render-scene)

(defscene input2-with-warning
  :params {:id "email-5"
           :label "Your email"
           :value "christian@kodemaker."
           :error? true
           :messages [{:kind :error
                       :icon :sasha.icons/warning
                       :text "Email seems incomplete"}]}
  render-scene)
