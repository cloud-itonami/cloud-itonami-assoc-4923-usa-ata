(ns association.facts
  "Industry rule/policy-statement catalog for the American Trucking
  Associations (ATA, Wikidata Q4745283) -- a 24th industry-association-
  level source (see cloud-itonami-assoc-6419-jpn-zenginkyo, -6512-jpn-sonpo,
  -6612-jpn-jsda, -6419-deu-bankenverband, -6612-usa-finra, -6512-usa-naic,
  -6920-jpn-jicpa, -6920-usa-aicpa, -6419-fra-fbf, -6511-jpn-seiho,
  -6910-jpn-nichibenren, -6810-jpn-recaj, -6411-jpn-boj, -6120-usa-ctia,
  -5110-usa-a4a, -3510-usa-eei, -2910-deu-vda, -5510-usa-ahla,
  -2100-usa-phrma, -4719-usa-nrf, -4100-usa-agc, -6020-usa-nab,
  -3600-usa-awwa for the first twenty-three) per ADR-2607141700
  (cloud-itonami-compliance-fact-federation). The FIRST entry aligned to
  ISIC 4923 (freight transport by road) -- a new industry code for this
  family. A rule not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/date.

  Both entries were directly WebFetch-verified against trucking.org's
  own pages. 'ATA: 90 Years and Rolling' states the exact incorporation
  date (Sept. 23, 1933) in its own text. The Safety policy-positions
  page introduces a NEW :kind, :policy-position -- ATA's official public
  advocacy/regulatory stance (CSA, ELDs, hours-of-service, drug/alcohol
  testing, driver licensing), distinct from :self-regulatory-code (a
  binding member commitment) and :governance-program (an org profile
  page): this is neither, it is the association's public position taken
  toward regulators/legislators. The page states NO single adoption
  date for the policy positions themselves (only unrelated news-article
  dates elsewhere on the site), so :association-rule/established-date
  is deliberately omitted here rather than fabricated from an adjacent
  date.")

(def catalog
  "assoc-slug -> vector of self-regulatory rule entries."
  {"ata"
   [{:association-rule/id "ata.founding-90-years-and-rolling"
     :association-rule/title "ATA: 90 Years and Rolling"
     :association-rule/association "ata"
     :association-rule/isic "4923"
     :association-rule/country "USA"
     :association-rule/kind :governance-program
     :association-rule/url "https://www.trucking.org/news-insights/ata-90-years-and-rolling"
     :association-rule/url-provenance :official-association-site
     :association-rule/established-date "1933-09-23"
     :association-rule/retrieved-at "2026-07-15"
     :association-rule/topic #{:governance}}
    {:association-rule/id "ata.safety-policy-positions"
     :association-rule/title "Safety (ATA Policy Positions)"
     :association-rule/association "ata"
     :association-rule/isic "4923"
     :association-rule/country "USA"
     :association-rule/kind :policy-position
     :association-rule/url "https://trucking.org/safety"
     :association-rule/url-provenance :official-association-site
     :association-rule/retrieved-at "2026-07-15"
     :association-rule/topic #{:safety}}]})

(defn spec-basis [assoc-slug] (get catalog assoc-slug))

(defn coverage
  ([] (coverage (keys catalog)))
  ([slugs]
   (let [have (filter catalog slugs)
         missing (remove catalog slugs)]
     {:requested (count slugs)
      :covered (count have)
      :covered-associations (vec (sort have))
      :missing-associations (vec (sort missing))
      :note (str "cloud-itonami-assoc-4923-usa-ata Wave 0 (ADR-2607141700): "
                 (count (get catalog "ata")) " ata entries seeded with an "
                 "official trucking.org citation. Extend "
                 "`association.facts/catalog`, never fabricate a rule id/url.")})))

(defn by-topic [assoc-slug topic]
  (filterv #(contains? (:association-rule/topic %) topic) (spec-basis assoc-slug)))
