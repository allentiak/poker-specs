(ns allentiak.poker.specs
  "Specs for the core API of poker."
  (:gen-class)
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]))

(comment
  (require '[clojure.spec.alpha :as s]
           '[clojure.spec.test.alpha :as st]))

(set! *warn-on-reflection* true)

