(ns acme.frontend.macros)

(defmacro pollen-read [& args]
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
                  (recur tail (conj result (str head))))))))]
    `(vec '~(process-args args))))

