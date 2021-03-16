(ns inventory.books
  (:require [clojure.spec.alpha :as s]))

; Validation with AND
(def n-gt-10 (s/and number? #(> % 10)))
(s/valid? n-gt-10 1)
(s/valid? n-gt-10 10)
(s/valid? n-gt-10 11)

; Validation with AND multiple predicates
(def n-gt-10-lt-100
  (s/and number? #(> % 10) #(< % 100)))
(s/valid? n-gt-10-lt-100 1)
(s/valid? n-gt-10-lt-100 15)
(s/valid? n-gt-10-lt-100 101)

; Validation with collections
(def coll-of-strings (s/coll-of string?))
(def collection-of-str '("Alice" "in" "wonderland"))
(s/valid? coll-of-strings collection-of-str)

(def books [{:title "2001" :author "Clarke" :copies 2}
            {:title "Emma" :author "Austen" :copies 10}
            {:title "Misery" :author "King" :copies 101}])


