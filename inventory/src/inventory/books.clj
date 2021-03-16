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

; Validation with CAT
(def s-n-s-n (s/cat :s1 string? :n1 number? :s2 string? :n2 number?))
(def movies ["Emma" 1815 "Jaws" 1974])
(s/valid? s-n-s-n movies)
(def movies2 ["Emma" 1815 1929 1974])
(s/valid? s-n-s-n movies2)

; Validation with KEYs
(s/def :inventory.books/title string?)
(s/def :inventory.books/author string?)
(s/def :inventory.books/copies number?)
(s/def :inventory.books/book
  (s/keys :req-un [:inventory.books/title ::author :inventory.books/copies]))

(def books [{:title "2001" :author "Clarke" :copies 2}
            {:title "Emma" :author "Austen" :copies 10}
            {:title "Misery" :responsible "King" :copies 101}])
(s/valid? :inventory.books/book (first books))
(s/valid? ::book (last books))


