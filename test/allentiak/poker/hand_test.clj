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

(def hand-with-two-pairs [[3 :diamonds] [3 :spades] [2 :hearts] [2 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-without-two-pairs [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest is-two-pairs-test
  (testing "two-pairs?"
    (is (hand/two-pairs? hand-with-two-pairs))))

(deftest is-not-two-pairs-test
  (testing "not two-pairs?"
    (is (not (hand/two-pairs? hand-without-two-pairs)))))
