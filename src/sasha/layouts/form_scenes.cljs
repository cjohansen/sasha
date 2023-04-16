(ns sasha.layouts.form-scenes
  (:require [portfolio.dumdom :refer-macros [defscene]]
            [sasha.components.form :refer [Form Receipt]]))

(defscene form
  (Form
   {:fields [{:id "name"
              :label "Name"}
             {:id "email"
              :label "Email"}
             {:id "phone"
              :label "Phone number"}
             {:id "country"
              :label "Country"}]
    :button {:text "Submit"
             :actions []}}))

(defscene form-with-errors
  (Form
   {:fields [{:id "name"
              :error? true
              :label "Name"
              :messages [{:kind :error
                          :icon :sasha.icons/warning
                          :text "Please enter your name"}]}
             {:id "email"
              :label "Email"
              :value "christian@kodemaker.no"}
             {:id "phone"
              :label "Phone number"
              :value "+4793417480"}
             {:id "country"
              :label "Country"
              :value "Norway"}]
    :button {:text "Submit"
             :disabled? true
             :actions []}}))

(defscene form-processing
  (Form
   {:fields [{:id "name"
              :label "Name"
              :value "Christian Johansen"
              :disabled? true}
             {:id "email"
              :label "Email"
              :value "christian@kodemaker.no"
              :disabled? true}
             {:id "phone"
              :label "Phone number"
              :value "+4793417480"
              :disabled? true}
             {:id "country"
              :label "Country"
              :value "Norway"
              :disabled? true}]
    :button {:text "Submitting, please hold"
             :disabled? true
             :spinner? true
             :actions []}}))

(defscene receipt
  (Receipt
   {:title "Thanks!"
    :icon :sasha.icons/thumbs-up
    :text "Your application is on its way, you'll hear from us shortly."
    :fields [{:label "Name"
              :value "Christian Johansen"}
             {:label "Email"
              :value "christian@kodemaker.no"}
             {:label "Phone number"
              :value "+4793417480"}
             {:label "Country"
              :value "Norway"}]}))
