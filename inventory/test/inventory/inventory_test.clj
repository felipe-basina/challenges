(ns inventory.inventory-test
  (:require [inventory.inventory :as inventory]
            [clojure.test :refer :all]))

(def books [{:title "2001" :author "Clarke" :copies 2}
            {:title "Emma" :author "Austen" :copies 10}
            {:title "Misery" :author "King" :copies 101}])

(deftest test-find-by-title
  (testing "find by title"
    (let [some-book (inventory/find-by-title "2001" books)]
      (is (not-empty some-book))
      (is (map? some-book))
      (is (contains? some-book :title))
      (is (contains? some-book :author))
      (is (contains? some-book :copies)))))

(deftest test-number-of-copies-of
  (testing "get the number of copies"
    (let [copies (inventory/number-of-copies-of "Emma" books)]
      (is (> copies 0))
      (is (= copies 10)))))