(ns allentiak.poker.hand-test
  (:require [allentiak.poker.hand :as hand]
            [clojure.test :refer [deftest is testing]]))

(def hand-with-a-pair [[3 :diamonds] [3 :spades] [1 :hearts] [2 :clubs] [4 :clubs]])
(def hand-without-a-pair [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest is-one-pair-test
  (testing "one-pair?"
    (is (hand/one-pair? hand-with-a-pair))))

(deftest is-not-one-pair-test
  (testing "not one-pair?"
    (is (not (hand/one-pair? hand-without-a-pair)))))
