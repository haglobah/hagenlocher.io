(ns acme.frontend.app
  (:require
    [reacl-c.main :as main]
    [reacl-c.core :as c :include-macros true]
    [reacl-c.dom :as d :include-macros true]))

(c/def-item app
  (d/div "Hello World"))

(defn init []
  (main/run (js/document.getElementById "root")
    app))
