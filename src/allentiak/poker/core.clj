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

(comment
  (value [2 :spades])
  ;; => 2
  (value [2 :hearts])
  ;; => 2
  (value [:jack :hearts])
  ;; => 11
  )

(s/fdef one-pair?
  :args ::specs/hand
  :ret boolean?)

(defn one-pair?
  [hand]
  (let [ranks (map first hand)]
    (= 1 (count (filter #(= % 2) (vals (frequencies ranks)))))))

(comment
  ;; FIXME: the card '[1 :hearts]' should not conform...
  ;; Maybe should check spec first?
  (one-pair? [[3 :diamonds] [3 :spades] [1 :hearts] [2 :clubs] [4 :clubs]])
  ;; => true

  ;; FIXME: the card '[1 :diamonds]' should not conform...
  ;; Maybe should check spec first?
  (one-pair? [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])
  ;; => false
  )

(s/fdef two-pairs?
  :args ::specs/hand
  :ret boolean?)

(defn two-pairs?
  [hand]
  (let [ranks (map first hand)]
    (= 2 (count (filter #(= % 2) (vals (frequencies ranks)))))))

(comment
  (two-pairs? [[3 :diamonds] [3 :spades] [2 :hearts] [2 :clubs] [4 :clubs]])
  ;; => true

  ;; FIXME: the card '[1 :diamonds]' should not conform...
  ;; Maybe should check spec first?
  (two-pairs? [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])
  ;; => false
  )

(s/fdef three-of-a-kind?
  :args ::specs/hand
  :ret boolean?)

(defn three-of-a-kind?
  [hand]
  (let [ranks (map first hand)]
    (boolean (seq (filter #(= % 3) (vals (frequencies ranks)))))))

(comment
  (three-of-a-kind? [[3 :diamonds] [3 :spades] [3 :hearts] [2 :clubs] [4 :clubs]])
;; => true

  ;; FIXME: the card '[1 :diamonds]' should not conform...
  ;; Maybe should check spec first?
  (three-of-a-kind? [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])
;; => false
  )

(s/fdef four-of-a-kind?
  :args ::specs/hand
  :ret boolean?)

(defn four-of-a-kind?
  [hand]
  (let [ranks (map first hand)]
    (boolean (seq (filter #(= % 4) (vals (frequencies ranks)))))))

(comment
  (four-of-a-kind? [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])
  ;; => true

  ;; FIXME: the card '[1 :diamonds]' should not conform...
  ;; Maybe should check spec first?
  (four-of-a-kind? [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])
  ;; => false
  )

(s/fdef full-house?
  :args ::specs/hand
  :ret boolean?)

(defn full-house?
  [hand]
  (and (one-pair? hand)
       (three-of-a-kind? hand)))

(comment
  (full-house? [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])
  ;; => false

  ;; FIXME: the card '[1 :diamonds]' should not conform...
  ;; Maybe should check spec first?
  (full-house? [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])
  ;; => false

  (full-house? [[3 :diamonds] [3 :spades] [3 :hearts] [2 :clubs] [2 :diamonds]])
  ;; => true
  )

(s/fdef flush?
  :args ::specs/hand
  :ret boolean?)

(defn flush?
  [hand]
  (let [suites (map second hand)]
    (boolean (seq (filter #(= % 5) (vals (frequencies suites)))))))

(comment
  (flush? [[3 :diamonds] [3 :spades] [3 :hearts] [3 :clubs] [4 :clubs]])
;; => false

  ;; FIXME: the card '[1 :diamonds]' should not conform...
  ;; Maybe should check spec first?
  (flush? [[1 :diamonds] [2 :diamonds] [3 :diamonds] [4 :diamonds] [5 :diamonds]])
;; => true
  )

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
   `points
   `one-pair?
   `two-pairs?
   `three-of-a-kind?
   `four-of-a-kind?
   `full-house?
   `flush?])

(defn instrument []
  (st/instrument fns-with-specs))

(defn unstrument []
  (st/unstrument fns-with-specs))
