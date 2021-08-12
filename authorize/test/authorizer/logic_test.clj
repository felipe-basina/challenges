(ns authorize.logic-test
  (:require [authorize.components.util :as util]
            [authorize.logic :as logic]
            [clojure.test :refer :all]))

(deftest test-transactions-reached-limit-in-interval?
  (testing "check transactions reached limit in interval"
    (let [last-transaction {:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"}
          oldest-transaction {:merchant "Burger King" :amount 14 :time "2019-02-13T09:56:00.000Z"}
          transactions (list last-transaction oldest-transaction)]
      (is (not (logic/transactions-reached-limit-in-interval? transactions 2))))))

(deftest test-transactions-reached-limit-in-interval?-a-few-seconds-difference
  (testing "check transactions reached limit in interval, a few seconds difference"
    (let [last-transaction {:merchant "Burger King" :amount 20 :time "2019-02-13T10:00:00.000Z"}
          oldest-transaction {:merchant "Burger King" :amount 14 :time "2019-02-13T09:59:37.000Z"}
          transactions (list last-transaction oldest-transaction)]
      (is (logic/transactions-reached-limit-in-interval? transactions 120000)))))

(deftest test-transactions-reached-limit-in-interval-as-last-time-less-than-oldest-time
  (testing "check transactions reached limit in interval, as last time less than oldest time"
    (let [last-transaction {:merchant "Burger King" :amount 20 :time "2019-02-13T09:56:00.000Z"}
          oldest-transaction {:merchant "Burger King" :amount 14 :time "2019-02-13T10:01:03.000Z"}
          transactions (list last-transaction oldest-transaction)]
      (is (not (logic/transactions-reached-limit-in-interval? transactions 2))))))

(deftest test-filter-transactions-by-merchant
  (testing "filter transactions by merchant"
    (let [habibs-transaction {:merchant "Habib's" :amount 20 :time "2019-02-13T09:56:00.000Z"}
          bk-transaction {:merchant "Burger King" :amount 20 :time "2019-02-13T09:56:00.000Z"}
          habibs-transaction2 { :merchant "Habib's" :amount 20 :time "2019-02-13T10:00:00.000Z"}
          transactions (list habibs-transaction2 bk-transaction habibs-transaction)
          filtered-transactions (logic/filter-transactions-by transactions
                                                              (fn [transaction] (= "Habib's" (:merchant transaction))))]
      (is (not-empty filtered-transactions))
      (is (= 2 (count filtered-transactions))))))

(deftest test-has-similar-transaction-in-interval
  (testing "check has similar transaction in interval"
    (let [habibs-transaction {:merchant "Habib's" :amount 20 :time "2019-02-13T09:56:00.000Z"}
          new-transaction {:merchant "Habib's" :amount 20 :time "2019-02-13T09:58:00.000Z"}
          transactions (list habibs-transaction)]
      (is (logic/has-similar-transaction-in-interval? transactions new-transaction 120000)))))

(deftest test-has-not-similar-transaction-in-interval
  (testing "check has not similar transaction in interval, different amount"
    (let [habibs-transaction {:merchant "Habib's" :amount 20 :time "2019-02-13T09:56:00.000Z"}
          new-transaction {:merchant "Habib's" :amount 17 :time "2019-02-13T09:58:00.000Z"}
          transactions (list habibs-transaction)]
      (is (not (logic/has-similar-transaction-in-interval? transactions new-transaction 2))))))

(deftest test-has-not-similar-transaction-in-interval-with-difference-time-greater-than-interval
  (testing "check has not similar transaction in interval, same transaction with difference time greater than interval"
    (let [habibs-transaction {:merchant "Habib's" :amount 20 :time "2019-02-13T09:56:00.000Z"}
          new-transaction {:merchant "Habib's" :amount 20 :time "2019-02-13T09:59:00.000Z"}
          transactions (list habibs-transaction)]
          (is (not (logic/has-similar-transaction-in-interval? transactions new-transaction 2))))))

(deftest test-difference-between-date-times
  (testing "difference between date times"
    (let [difference (logic/calc-difference-in-milliseconds
                       (util/convert-str-datetime "2019-02-13T10:02:58.000Z")
                       (util/convert-str-datetime "2019-02-13T10:00:00.000Z"))]
      (is (> difference 0)))))