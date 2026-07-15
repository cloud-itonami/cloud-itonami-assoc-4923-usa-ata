(ns association.facts-test
  (:require [clojure.test :refer [deftest is]]
            [association.facts :as facts]))

(deftest ata-has-spec-basis
  (let [sb (facts/spec-basis "ata")]
    (is (= 2 (count sb)))
    (is (every? #(= "4923" (:association-rule/isic %)) sb))
    (is (every? #(= "USA" (:association-rule/country %)) sb))))

(deftest unknown-association-has-no-spec-basis
  (is (nil? (facts/spec-basis "wef")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["ata" "wef"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["wef"] (:missing-associations c)))))

(deftest by-topic-filters
  (is (= ["ata.safety-policy-positions"]
         (mapv :association-rule/id (facts/by-topic "ata" :safety))))
  (is (empty? (facts/by-topic "ata" :labor)))
  (is (empty? (facts/by-topic "wef" :governance))))
