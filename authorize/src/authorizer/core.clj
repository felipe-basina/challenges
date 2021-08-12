(ns authorize.core
  (:require [authorize.adapter :as adapter])
  (:gen-class))

(defn -main [& args]
  (adapter/process-input))
