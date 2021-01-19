(ns allentiak.poker.core-test
  (:require [allentiak.poker.core :as sut]
            [clojure.test :as t]))

(def hand-with-a-pair [[3 :diamonds] [3 :spades] [1 :hearts] [2 :clubs] [4 :clubs]])
(def hand-without-a-pair [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])

(t/deftest is-one-pair-test
  (t/testing "one-pair?"
    (t/is (hand/one-pair? hand-with-a-pair))))

(t/deftest is-not-one-pair-test
  (t/testing "not one-pair?"
    (t/is (not (sut/one-pair? hand-without-a-pair)))))
