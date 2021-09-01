(ns GreedyAlgorithms.maxlootvalue-test
  (:require [clojure.test :as t]
            [GreedyAlgorithms.maxlootvalue :refer [maxval]]))

(t/deftest no-fraccional)

(t/deftest fraccional)