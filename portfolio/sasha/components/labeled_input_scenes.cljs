(ns sasha.components.labeled-input-scenes
  (:require [sasha.components.labeled-input :refer [LabeledInput]]
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
