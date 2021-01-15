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
  :args ::specs/hand
  :ret nat-int?)

(defn value
  [hand]
  (cond
    (s/conform ::specs/royal-flush hand)     10
    (s/conform ::specs/straight-flush hand)  9
    (s/conform ::specs/four-of-a-kind hand)  8
    (s/conform ::specs/full-house hand)      7
    (s/conform ::specs/flush hand)           6
    (s/conform ::specs/straight hand)        5
    (s/conform ::specs/three-of-a-kind hand) 4
    (s/conform ::specs/two-pairs hand)       3
    (s/conform ::specs/one-pair hand)        2
    (s/conform ::specs/high-card)            1))

(def ^:private fns-with-specs
  [`hand/value])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
