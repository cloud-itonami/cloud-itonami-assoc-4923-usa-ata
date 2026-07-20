(ns association-facts-test
  (:require [clojure.java.io :as io] [clojure.java.shell :as shell]
            [clojure.test :refer [deftest is testing]]
            [kotoba.compiler.core :as compiler] [kotoba.compiler.ir :as ir]))
(def source (slurp "src/association_facts.kotoba"))
(defn call [kir function & args] (ir/execute kir function (vec args)))
(defn present [option] (when (second option) (nth option 2)))
(def fields ["id" "title" "association" "isic" "country" "kind" "url"
             "url-provenance" "established-date" "retrieved-at"])
(def expected
  [{"id" "ata.founding-90-years-and-rolling" "title" "ATA: 90 Years and Rolling"
    "association" "ata" "isic" "4923" "country" "USA" "kind" "governance-program"
    "url" "https://www.trucking.org/news-insights/ata-90-years-and-rolling"
    "url-provenance" "official-association-site" "established-date" "1933-09-23"
    "retrieved-at" "2026-07-15"}
   {"id" "ata.safety-policy-positions" "title" "Safety (ATA Policy Positions)"
    "association" "ata" "isic" "4923" "country" "USA" "kind" "policy-position"
    "url" "https://trucking.org/safety" "url-provenance" "official-association-site"
    "established-date" nil "retrieved-at" "2026-07-15"}])

(deftest reference-preserves-fields-date-precision-and-topics
  (let [kir (:kir (compiler/compile-source source :js-kotoba-v1))
        observed (mapv (fn [i] (into {} (map (fn [f] [f (present (call kir 'entry-field "ata" i f))]) fields))) [0 1])]
    (is (= expected observed))
    (is (= ["1933-09-23" nil] (mapv #(present (call kir 'entry-field "ata" % "established-date")) [0 1])))
    (is (= [1 1] (mapv #(call kir 'topic-count "ata" %) [0 1])))
    (is (= ["governance" "safety"] (mapv #(present (call kir 'topic "ata" % 0)) [0 1])))
    (is (= "ata.safety-policy-positions" (present (call kir 'by-topic-id "ata" "safety" 0))))
    (is (= #{} (set (:effects kir))))
    (testing "unknown values and invalid indexes fail closed"
      (is (zero? (call kir 'entry-count "wef")))
      (is (nil? (present (call kir 'entry-field "ata" -1 "id"))))
      (is (nil? (present (call kir 'entry-field "ata" 2 "id"))))
      (is (nil? (present (call kir 'entry-field "ata" 1 "established-date"))))
      (is (nil? (present (call kir 'topic "ata" 0 1))))
      (is (zero? (call kir 'by-topic-count "ata" "labor")))
      (is (nil? (present (call kir 'by-topic-id "ata" "safety" 1)))))))

(defn compiler-root []
  (nth (iterate #(.getParent ^java.nio.file.Path %)
                (java.nio.file.Path/of (.toURI (io/resource "kotoba/compiler/core.clj")))) 4))
(defn base64 [value] (.encodeToString (java.util.Base64/getEncoder) value))
(deftest restricted-javascript-and-typed-wasm-conform-semantically
  (let [javascript (compiler/compile-source source :js-kotoba-v1)
        wasm (compiler/compile-source source :wasm32-browser-kotoba-v1)
        js64 (base64 (.getBytes ^String (:source javascript) "UTF-8")) wasm64 (base64 ^bytes (:bytes wasm))
        probe (shell/sh "node" "--input-type=module" "-e"
                (str "import(process.argv[1]).then(async host=>{const j=await import('data:text/javascript;base64," js64 "');"
                     "const w=await host.instantiateKotoba(Buffer.from(process.argv[2],'base64'));const run=x=>{"
                     "if(x['entry-count']('ata')!==2n||x['entry-field']('ata',0n,'established-date')[2]!=='1933-09-23'||x['entry-field']('ata',1n,'established-date')[1]!==false)throw Error('dates');"
                     "if(x['topic-count']('ata',0n)!==1n||x['topic']('ata',0n,0n)[2]!=='governance'||x['topic']('ata',1n,0n)[2]!=='safety')throw Error('topics');"
                     "if(x['by-topic-id']('ata','safety',0n)[2]!=='ata.safety-policy-positions'||x['topic']('ata',0n,1n)[1]!==false)throw Error('query');};"
                     "run(j.instantiateKotoba({}));run(w.instance.exports);}).catch(e=>{console.error(e);process.exit(99)})")
                (.toString (.toUri (.resolve (compiler-root) "runtime/browser-host.mjs"))) wasm64)]
    (is (zero? (:exit probe)) (str (:out probe) (:err probe)))))
(deftest production-source-authority
  (is (= ["src/association_facts.kotoba"]
         (->> (file-seq (io/file "src")) (filter #(.isFile %)) (map str) sort vec))))
