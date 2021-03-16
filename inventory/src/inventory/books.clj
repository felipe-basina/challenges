(ns inventory.books
  (:require [clojure.spec.alpha :as s]))

(def n-gt-10 (s/and number? #(> % 10)))

(s/valid? n-gt-10 1)
(s/valid? n-gt-10 10)
(s/valid? n-gt-10 11)

(def n-gt-10-lt-100
  (s/and number? #(> % 10) #(< % 100)))

(s/valid? n-gt-10-lt-100 1)
(s/valid? n-gt-10-lt-100 15)
(s/valid? n-gt-10-lt-100 101)

(def books [{:title "2001" :author "Clarke" :copies 2}
            {:title "Emma" :author "Austen" :copies 10}
            {:title "Misery" :author "King" :copies 101}])


