(ns inventory.inventory-generators
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(def title-gen gen/string-alphanumeric)
(println (gen/sample title-gen))

(def author-gen gen/string-alphanumeric)
(println (gen/sample author-gen))

(def copies-gen gen/nat)
(println (gen/sample copies-gen))