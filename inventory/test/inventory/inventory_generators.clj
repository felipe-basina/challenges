(ns inventory.inventory-generators
  (:require [inventory.inventory :as inventory]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
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

(def book-gen
  (gen/hash-map :title title-gen :author author-gen :copies copies-gen))
(println (gen/sample book-gen))

(def inventory-gen (gen/not-empty (gen/vector book-gen)))
(println (gen/sample inventory-gen))

(def inventory-and-book-gen
  (gen/let [inventory inventory-gen
            book (gen/elements inventory)]
           {:inventory inventory :book book}))
(println "\nInventory and book generated" (gen/sample inventory-and-book-gen))

(tc/quick-check 50
                (prop/for-all [i-and-b inventory-and-book-gen]
                              (= (inventory/find-by-title (-> i-and-b
                                                              :book
                                                              :title)
                                                          (:inventory i-and-b))
                                 (:book i-and-b))))