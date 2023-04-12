(ns sasha.portfolio
  (:require [dumdom.component]
            [portfolio.ui :as ui]
            [sasha.components.button-scenes]
            [sasha.components.completion-input-scenes]
            [sasha.components.spinner-scenes]
            [sasha.components.swiper-scenes]
            [sasha.icons-scenes]))

::sasha.components.button-scenes/keep
::sasha.components.completion-input-scenes/keep
::sasha.components.spinner-scenes/keep
::sasha.components.swiper-scenes/keep
::sasha.icons-scenes/keep

(set! dumdom.component/*render-eagerly?* true)

(ui/start!
 {:config
  {:css-paths
   ["/css/sasha.css"
    "/css/theme-pinky.css"]

   :grid/options [{:title "8 x 8px"
                   :value {:grid/offset 0
                           :grid/size 8
                           :grid/group-size 8}}
                  {:title "No grid"
                   :value {:grid/size 0}}]}})
