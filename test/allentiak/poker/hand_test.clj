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

(def hand-without-full-house--no-pair [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-without-full-house--straight [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(def hand-with-full-house [[3 :diamonds] [3 :spades] [3 :hearts] [2 :clubs] [2 :diamonds]])

(deftest full-house-test
  (testing "full-house?"
    (is (hand/full-house? hand-with-full-house)))
  (testing "not full-house?"
    (is (not (hand/full-house? hand-without-full-house--no-pair)))
    (is (not (hand/full-house? hand-without-full-house--straight)))))

(def hand-without-flush [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])
;; TODO: decouple data access from its implementation
;;
;; (hand-create (card-create 3 :diamonds)
;;              (card-create 3 :spades)
;;              (card-create 3 :hearts)
;;              (card-create 3 :clubs)
;;              (card-create 4 :clubs))

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-with-flush [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest flush-test
  (testing "flush?"
    (is (hand/flush? hand-with-flush)))
  (testing "not flush?"
    (is (not (hand/flush? hand-without-flush)))))

(def non-royal-card-2 [2 :spades])
(def royal-card-jack [:jack :hearts])

(deftest value-test
  (testing "value of a non-royal card"
    (is (= 2 (hand/value non-royal-card-2))))
  (testing "value of a royal card"
    (is (= 11 (hand/value royal-card-jack)))))

(def hand-without-straight [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-with-straight [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :hearts] [5 :diamonds]])

(deftest straight-test
  (testing "straight?"
    (is (hand/straight? hand-with-straight)))
  (testing "not straight?"
    (is (not (hand/straight? hand-without-straight)))))

(def hand-without-straight-flush [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-with-straight-flush [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(deftest straight-flush-test
  (testing "straight-flush?"
    (is (hand/straight-flush? hand-with-straight-flush)))
  (testing "not straight-flush?"
    (is (not (hand/straight-flush? hand-without-straight-flush)))))

(def hand-without-royal-flush [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])

;; FIXME: the card '[1 :diamonds]' should not conform...
;; Maybe should check spec first?
(def hand-with-royal-flush [[10 :diamonds] [:jack :diamonds] [:queen :diamonds] [:king :diamonds] [:ace :diamonds]])

(deftest royal-flush-test
  (testing "royal-flush?"
    (is (hand/royal-flush? hand-with-royal-flush)))
  (testing "not royal-flush?"
    (is (not (hand/royal-flush? hand-without-royal-flush)))))
