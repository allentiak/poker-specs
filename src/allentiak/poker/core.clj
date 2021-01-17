(ns allentiak.poker.core
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
  :ret nat-int?)

(defn value
  "returns the numeric value of a card. For now, it only supports 'high' rules."
  [card]
  (let [rank (first card)]
    (cond
      (or (>= rank 2) (<= rank 10)) (identity rank)
      (= rank :jack)                11
      (= rank :queen)               12
      (= rank :king)                13
      (= rank :ace)                 14)))

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

(def ^:private fns-with-specs
  [`value
   `points])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
