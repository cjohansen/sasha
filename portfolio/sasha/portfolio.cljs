(ns sasha.portfolio
  (:require [dumdom.component]
            [portfolio.ui :as ui]
            [sasha.components.accordion-scenes]
            [sasha.components.button-scenes]
            [sasha.components.completion-input-scenes]
            [sasha.layouts.form-scenes]
            [sasha.components.labeled-input-scenes]
            [sasha.components.spinner-scenes]
            [sasha.components.swiper-scenes]
            [sasha.icons-scenes]))

::sasha.components.accordion-scenes/keep
::sasha.components.button-scenes/keep
::sasha.components.completion-input-scenes/keep
::sasha.layouts.form-scenes/keep
::sasha.components.labeled-input-scenes/keep
::sasha.components.spinner-scenes/keep
::sasha.components.swiper-scenes/keep
::sasha.icons-scenes/keep

(set! dumdom.component/*render-eagerly?* true)

(ui/start!
 {:config
  {:css-paths
   ["/css/sasha.css"
    "/css/theme-pinky.css"]

   :background/options [{:id :light
                         :title "Light"
                         :value {:background/background-color "#fff"
                                 :background/body-class "light"}}
                        {:id :dark
                         :title "Dark"
                         :value {:background/background-color "#282828"
                                 :background/body-class "dark"}}]

   :background/default-option-id :light

   :grid/options [{:title "8 x 8px"
                   :value {:grid/offset 0
                           :grid/size 8
                           :grid/group-size 8}}
                  {:title "No grid"
                   :value {:grid/size 0}}]}})
