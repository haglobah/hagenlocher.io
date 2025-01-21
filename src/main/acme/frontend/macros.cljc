(ns acme.frontend.macros
  (:require [clojure.string :as str]))

(defn inspect [form]
  (print (pr-str form))
  form)

(defmacro my-slurp [& args]
  (apply clojure.core/slurp args))

(defmacro pollen-read [& args]
  (println-str (pr-str args))
  (let [process-args
        (fn [args]
          (loop [remaining args
                 result []]
            (cond
              (empty? remaining) result
              :else
              (let [[head & tail] remaining]
                (cond
                  (= head '◊) (recur (rest tail) (conj result (eval (second remaining))))
                  :else
                  (recur tail (conj result (str head))))))))
        vectorized-args (inspect (vec (process-args args)))
        result (str/join " " vectorized-args)]
    result))
