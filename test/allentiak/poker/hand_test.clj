(ns allentiak.poker.hand-test
  (:require [allentiak.poker.hand :as hand]
            [clojure.test :refer [deftest is testing]]))

(def hand-with-one-pair [[3 :diamonds] [3 :spades] [1 :hearts] [2 :clubs] [4 :clubs]])
(def hand-without-one-pair [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest one-pair-test
  (testing "one-pair?"
    (is (hand/one-pair? hand-with-one-pair)))
  (testing "not one-pair?"
    (is (not (hand/one-pair? hand-without-one-pair)))))

(def hand-with-two-pairs [[3 :diamonds] [3 :spades] [2 :hearts] [2 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-without-two-pairs [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest two-pairs-test
  (testing "two-pairs?"
    (is (hand/two-pairs? hand-with-two-pairs)))
  (testing "not two-pairs?"
    (is (not (hand/two-pairs? hand-without-two-pairs)))))

(def hand-with-three-of-a-kind [[3 :diamonds] [3 :spades] [3 :hearts] [2 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-without-three-of-a-kind [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest three-of-a-kind-test
  (testing "three-of-a-kind?"
    (is (hand/three-of-a-kind? hand-with-three-of-a-kind)))
  (testing "not three-of-a-kind?"
    (is (not (hand/three-of-a-kind? hand-without-three-of-a-kind)))))

(def hand-with-four-of-a-kind [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-without-four-of-a-kind [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest four-of-a-kind-test
  (testing "four-of-a-kind?"
    (is (hand/four-of-a-kind? hand-with-four-of-a-kind)))
  (testing "not four-of-a-kind?"
    (is (not (hand/four-of-a-kind? hand-without-four-of-a-kind)))))
