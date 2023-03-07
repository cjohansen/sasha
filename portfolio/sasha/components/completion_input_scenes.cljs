(ns sasha.components.completion-input-scenes
  (:require [clojure.string :as str]
            [portfolio.dumdom :refer-macros [defscene defns]]
            [sasha.components.completion-input :refer [CompletionInput]]))

(defns "Completion Input"
  :group :components)

(defscene empty-input
  (CompletionInput {}))

(defscene input-with-placeholder
  (CompletionInput {:placeholder "Write in it"}))

(defscene input-with-placeholder-and-value
  (CompletionInput {:placeholder "Write in it"
                    :value "I am writing"}))

(defscene input-with-suggestion
  (CompletionInput {:value "I am writing"
                    :completion "I am writing to you"}))

(def completions
  ["Bananas"
   "Apples"
   "Oranges"
   "Orangutangs"
   "Orange juice"
   "Oracle"])

(defscene input-with-live-suggestions
  :param (atom {})
  [store]
  (let [{:keys [text]} @store]
    (CompletionInput
     {:value text
      :placeholder "Try completions"
      :completion (when-not (empty? text)
                    (->> completions
                         (filter #(str/starts-with? % text))
                         first))
      :onInput (fn [e]
                 (swap! store assoc :text (.. e -target -value)))})))
