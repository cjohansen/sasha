(ns sasha.build
  (:require [optimus.assets :as assets]
            [optimus.export :as export]))

(def assets
  (assets/load-assets
   "public"
   [#"/index.html"
    #"/css/sasha.css"
    #"/css/theme-pinky.css"
    #"/portfolio/prism.js"
    #"/portfolio/styles/portfolio.css"
    #"/portfolio/canvas.html"]))

(defn -main [& args]
  (export/save-assets assets "target"))
