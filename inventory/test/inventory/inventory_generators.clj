(ns inventory.inventory-generators
  (:require [inventory.inventory :as inventory]
            [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

;(def title-gen gen/string-alphanumeric)
(def title-gen (gen/such-that not-empty gen/string-alphanumeric))
(println (gen/sample title-gen))

;(def author-gen gen/string-alphanumeric)
(def author-gen (gen/such-that not-empty gen/string-alphanumeric))
(println (gen/sample author-gen))

;(def copies-gen gen/nat)
(def copies-gen (gen/such-that (complement zero?) gen/nat))
(println (gen/sample copies-gen))