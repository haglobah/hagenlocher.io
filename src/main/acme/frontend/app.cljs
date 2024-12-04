(ns acme.frontend.app
  (:require
    [fr.jeremyschoffen.prose.alpha.eval.sci :as eval-sci]
    [fr.jeremyschoffen.prose.alpha.reader.core :as reader]
    [fr.jeremyschoffen.prose.alpha.reader.grammar :as g]
    ;; [cljs.core]
    [acme.frontend.slurp :include-macros true :refer [slurp]]
    [reacl-c.main :as main]
    [reacl-c.core :as c :include-macros true]
    [reacl-c.dom :as d :include-macros true]))

(c/def-item app
  (->> (slurp "src/main/acme/frontend/example.cljs")
       g/parser
       reader/clojurize
       eval-sci/eval-forms
       (filter c/item?)
       (apply c/fragment)
      ;; pr-str
       )
  )

(defn init []
  (main/run (js/document.getElementById "root")
    app))
