(ns myapp.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main [& args]
  (let [ln (str/split (slurp (java.io.BufferedReader. *in*)) #"\n")]
    (loop [contents ln]
      (if (not-empty contents)
        (do
          (println (first contents))
          (recur (rest contents)))
        (println "Total" (count ln))))))
