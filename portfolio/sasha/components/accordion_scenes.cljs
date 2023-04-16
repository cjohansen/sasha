(ns sasha.components.accordion-scenes
  (:require [portfolio.dumdom :refer-macros [defscene]]
            [sasha.components.accordion :refer [Accordion]]))

(defscene collapsed-accordion
  (Accordion
   {:items [{:title "What is a scene?"
             :actions []
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}
            {:title "What is a collection?"
             :actions []
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}
            {:title "What can't Portfolio do?"
             :actions []
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}]}))

(defscene single-expanded-bellow
  (Accordion
   {:items [{:title "What is a scene?"
             :actions []
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}
            {:title "What is a collection?"
             :actions []
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 180}
             :content {:text "A collection is a bunch of related scenes. The contents of a collection can be rendered at the same time, and the collection can specify default configuration that controls the rendering of all scenes in it."}}
            {:title "What can't Portfolio do?"
             :actions []
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}]}))

(defn prepare-accordion [store]
  (let [accordion @store]
    (update
     accordion
     :items
     (fn [items]
       (map-indexed
        (fn [idx item]
          (cond-> (assoc item :on-click (fn [_] (swap! store update-in [:items idx :expanded?] not)))
            (:expanded? item)
            (assoc-in [:toggle :rotation] -180)

            (not (:expanded? item))
            (dissoc :content)))
        items)))))

(defscene animating-bellow
  :param (atom
          {:items
           [{:title "What is a scene?"
             :content {:text "A scene is an instance of a component. The dataset provokes rendering of a specific UI state."}
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}
            {:title "What is a collection?"
             :content {:text "A collection is a bunch of related scenes. The contents of a collection can be rendered at the same time, and the collection can specify default configuration that controls the rendering of all scenes in it."}
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}
            {:title "What can't Portfolio do?"
             :content {:text "It can't write UI components for you. Yet. Maybe some AI will help."}
             :toggle {:icon :sasha.icons/caret-down
                      :rotation 0}}]})
  [store]
  (Accordion (prepare-accordion store)))
