(ns allentiak.poker.specs
  "Specs for the core API of poker."
  (:gen-class)
  (:require [clojure.spec.alpha :as s]))

(comment
  (require '[clojure.spec.alpha :as s])
  ;; => nil
  )

(set! *warn-on-reflection* true)
;; => true

(s/def ::suit #{:clubs :diamonds :hearts :spades})
;; => :allentiak.poker.specs/suit

(comment
  (s/conform ::suit :clubs);; => :clubs
  (doc ::suit)
  ;; => nil
  )

(s/def ::royal-rank #{:jack :queen :king :ace})
;; => :allentiak.poker.specs/royal-rank

(comment
  (s/conform ::royal-rank :jack);; => :jack
  (s/conform ::royal-rank :blah)
  ;; => :clojure.spec.alpha/invalid
  )

(s/def ::non-royal-rank (s/int-in 2 11))
;; => :allentiak.poker.specs/non-royal-rank
(comment
  (s/conform ::non-royal-rank 1)
  ;; => :clojure.spec.alpha/invalid
  (s/conform ::non-royal-rank 2)
  ;; => 2
  (s/conform ::non-royal-rank 10)
  ;; => 10
  (s/conform ::non-royal-rank 11)
  ;; => :clojure.spec.alpha/invalid
  )


;; cannot make it work using #{}, s/alt, or s/or


(s/def ::r1 #{::non-royal-rank ::royal-rank})
;; => :allentiak.poker.specs/r1
(s/def ::r2 (s/or ::royal-rank ::non-royal-rank))
;; => :allentiak.poker.specs/r2
(s/def ::r3 (s/alt ::royal-rank ::non-royal-rank))
;; => :allentiak.poker.specs/r3

;; TODO: find out why it only works like this.
(s/def ::rank (into #{:jack :queen :king :ace} (range 2 11)))
;; => :allentiak.poker.specs/rank

(comment
  (s/conform ::r1 9)
  ;; => :clojure.spec.alpha/invalid
  (s/conform ::r1 :jack)
  ;; => :clojure.spec.alpha/invalid
  (s/conform ::r2 9)
  ;; => [:allentiak.poker.specs/royal-rank 9]
  (s/conform ::r2 :jack)
  ;; => :clojure.spec.alpha/invalid
  (s/conform ::r3 9)
  ;; => :clojure.spec.alpha/invalid
  (s/conform ::r3 :jack)
  ;; => :clojure.spec.alpha/invalid
  (s/conform ::rank 9)
  ;; => 9
  (s/conform ::rank :jack)
  ;; => :jack
  )

(s/def ::card (s/tuple ::rank ::suit));; => :allentiak.poker.specs/card

(comment
  (s/conform ::card [3 :diamonds]);; => [3 :diamonds]
  (s/conform ::card [:jack :spades])
  ;; => [:jack :spades]
  (s/conform ::card [1 :spades])
;; => :clojure.spec.alpha/invalid
  )

(s/def ::hand (s/coll-of ::card :kind vector? :count 5 :distinct true))
;; => :allentiak.poker.specs/hand

(comment
  (s/conform ::hand [[3 :diamonds] [2 :spades] [:ace :hearts] [:ace :diamonds] [10 :hearts]]);; => [[3 :diamonds] [2 :spades] [:ace :hearts] [:ace :diamonds] [10 :hearts]]
  (s/conform ::hand [[3 :diamonds] [1 :spades] [:ace :hearts] [:ace :diamonds] [10 :hearts]]);
;; => :clojure.spec.alpha/invalid
  )
