(ns acme.frontend.app
  (:require
   [fr.jeremyschoffen.prose.alpha.eval.sci :as eval-sci]
   [fr.jeremyschoffen.prose.alpha.reader.core :as reader]
   [fr.jeremyschoffen.prose.alpha.reader.grammar :as g]
    ;; [cljs.core]
   [acme.frontend.macros :include-macros true :refer [my-slurp pollen-read]]
   [acme.readers]
   [clojure.edn]
   [reacl-c.main :as main]
   [reacl-c.core :as c :include-macros true]
   [reacl-c.dom :as d :include-macros true]))

(c/def-item app
  (->> (my-slurp "src/main/acme/frontend/example.cljs")
       g/parser
       reader/clojurize
       eval-sci/eval-forms
       (filter c/item?)
       (apply c/fragment)
      ;; pr-str
       )
  )

(comment
  (def a 1)
  (defn html-wrap [arg] (str "<h1>" arg "</h1>"))
  (pollen-read ho ho haha ◊(html-wrap "hehe") ich kann hier schreiben)
  (clojure.edn/read-string {:readers {'bar (fn [it] it)}} "#bar hoho")
  )

(defn init []
  (main/run (js/document.getElementById "root")
    app))
