(ns allentiak.poker.specs-test
  "Stub test namespace for the specs of poker.

  For the moment, we are trying to find a namespace where to use (and 'test') the specs."
  (:require [clojure.test :refer [deftest is testing]]
            [allentiak.poker.specs :as specs]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as sg]))

(set! *warn-on-reflection* true)

(comment
  (require '[clojure.test :refer [deftest is testing]]
           '[allentiak.poker.specs :as specs]
           '[clojure.spec.alpha :as s]
           '[clojure.spec.gen.alpha :as sg])
  ;; => nil
  )

(comment
  (sg/sample (s/gen ::specs/hand))
  ;; => ([[5 :clubs] [:king :hearts] [9 :hearts] [8 :hearts] [10 :hearts]] [[:queen :clubs] [:king :clubs] [7 :hearts] [10 :spades] [7 :diamonds]] [[7 :diamonds] [2 :diamonds] [3 :hearts] [9 :clubs] [10 :hearts]] [[3 :spades] [:queen :hearts] [:jack :spades] [9 :spades] [5 :spades]] [[2 :diamonds] [4 :clubs] [:king :diamonds] [:jack :hearts] [:king :clubs]] [[7 :diamonds] [9 :clubs] [8 :diamonds] [:queen :spades] [9 :hearts]] [[4 :hearts] [4 :spades] [:ace :spades] [4 :diamonds] [6 :spades]] [[8 :hearts] [8 :clubs] [6 :clubs] [:queen :spades] [:king :hearts]] [[5 :spades] [9 :hearts] [6 :diamonds] [9 :spades] [5 :diamonds]] [[9 :spades] [8 :spades] [:ace :hearts] [:king :spades] [3 :diamonds]])
  )

(deftest basic-tests
  (testing "hand generator"
    (is (seq? (sg/sample (s/gen ::specs/hand))))))
