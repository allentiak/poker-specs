(ns allentiak.poker.hand
  "Main namespace for the game. This includes all transformation functions.

  In addition, there is an `instrument` function that provides a simple
  way to instrument all of the `poker.core` functions, and `unstrument`
  to undo that."

  (:require [allentiak.poker.specs :as specs]
            [clojure.spec.test.alpha :as st]
            [clojure.spec.alpha :as s]))

(set! *warn-on-reflection* true)

(s/fdef value
  :args ::specs/card
  :ret pos-int?)

(defn value
  "returns the numeric value of a card. For now, it only supports 'high' rules."
  [card]
  (let [rank (first card)]
    (cond
      (= rank :jack)                11
      (= rank :queen)               12
      (= rank :king)                13
      (= rank :ace)                 14
      ;; this comparison should be the last one to avoid a casting exception
      (or (>= rank 2) (<= rank 10)) (identity rank))))

(s/fdef one-pair?
  :args ::specs/hand
  :ret boolean?)

(defn one-pair?
  [hand]
  (let [ranks (map first hand)]
    (= 1 (count (filter #(= % 2) (vals (frequencies ranks)))))))

(s/fdef two-pairs?
  :args ::specs/hand
  :ret boolean?)

(defn two-pairs?
  [hand]
  (let [ranks (map first hand)]
    (= 2 (count (filter #(= % 2) (vals (frequencies ranks)))))))

(s/fdef three-of-a-kind?
  :args ::specs/hand
  :ret boolean?)

(defn three-of-a-kind?
  [hand]
  (let [ranks (map first hand)]
    (boolean (seq (filter #(= % 3) (vals (frequencies ranks)))))))

(s/fdef four-of-a-kind?
  :args ::specs/hand
  :ret boolean?)

(defn four-of-a-kind?
  [hand]
  (let [ranks (map first hand)]
    (boolean (seq (filter #(= % 4) (vals (frequencies ranks)))))))

(s/fdef full-house?
  :args ::specs/hand
  :ret boolean?)

(defn full-house?
  [hand]
  (and (one-pair? hand)
       (three-of-a-kind? hand)))

(s/fdef flush?
  :args ::specs/hand
  :ret boolean?)

(defn flush?
  [hand]
  (let [suites (map second hand)]
    (boolean (seq (filter #(= % 5) (vals (frequencies suites)))))))

(s/fdef straight?
  :args ::specs/hand
  :ret boolean?)

(defn straight?
  [hand]
  (let [actual-values (sort (map value hand))
        first-expected-value (first actual-values)
        expected-values (range first-expected-value (+ 5 first-expected-value))]
    (= actual-values expected-values)))

(s/fdef straight-flush?
  :args ::specs/hand
  :ret boolean?)

(defn straight-flush?
  [hand]
  (and (straight? hand)
       (flush? hand)))

(s/fdef royal-flush?
  :args ::specs/hand
  :ret boolean?)

(defn royal-flush?
  [hand]
  (let [values (sort (map value hand))]
    (and (straight-flush? hand)
         (= 10 (first values)))))

(s/fdef highest-value
  :args ::specs/hand
  :ret pos-int?)

(defn highest-value
  [hand]
  (apply max (sort (map value hand))))

(s/fdef points
  :args ::specs/hand
  :ret nat-int?)

(defn points
  [hand]
  (cond
    (royal-flush? hand)     10
    (straight-flush? hand)  9
    (four-of-a-kind? hand)  8
    (full-house? hand)      7
    (flush? hand)           6
    (straight? hand)        5
    (three-of-a-kind? hand) 4
    (two-pairs? hand)       3
    (one-pair? hand)        2
    :else                   1))

(s/fdef equal-value?
  :arges (s/cat ::specs/hand ::specs/hand)
  :ret boolean?)

(defn equal-value?
  [hand1 hand2]
  (and (= (points hand1) (points hand2))
       (= (highest-value hand1) (highest-value hand2))))

(def ^:private fns-with-specs
  [`value
   `points
   `one-pair?
   `two-pairs?
   `three-of-a-kind?
   `four-of-a-kind?
   `full-house?
   `flush?
   `straight?
   `straight-flush?
   `royal-flush?
   `highest-value
   `equal-value?])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
