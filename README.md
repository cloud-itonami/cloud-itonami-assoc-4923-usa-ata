# cloud-itonami-assoc-4923-usa-ata

Industry rule/policy-statement catalog for the **American Trucking
Associations** (ATA) — the FIRST entry aligned to **ISIC 4923** (freight
transport by road), a new industry code for this family. Alongside
[`cloud-itonami-assoc-6419-jpn-zenginkyo`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-jpn-zenginkyo),
[`-6512-jpn-sonpo`](https://github.com/cloud-itonami/cloud-itonami-assoc-6512-jpn-sonpo),
[`-6612-jpn-jsda`](https://github.com/cloud-itonami/cloud-itonami-assoc-6612-jpn-jsda),
[`-6419-deu-bankenverband`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-deu-bankenverband),
[`-6612-usa-finra`](https://github.com/cloud-itonami/cloud-itonami-assoc-6612-usa-finra),
[`-6512-usa-naic`](https://github.com/cloud-itonami/cloud-itonami-assoc-6512-usa-naic),
[`-6920-jpn-jicpa`](https://github.com/cloud-itonami/cloud-itonami-assoc-6920-jpn-jicpa),
[`-6920-usa-aicpa`](https://github.com/cloud-itonami/cloud-itonami-assoc-6920-usa-aicpa),
[`-6419-fra-fbf`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-fra-fbf),
[`-6511-jpn-seiho`](https://github.com/cloud-itonami/cloud-itonami-assoc-6511-jpn-seiho),
[`-6910-jpn-nichibenren`](https://github.com/cloud-itonami/cloud-itonami-assoc-6910-jpn-nichibenren),
[`-6810-jpn-recaj`](https://github.com/cloud-itonami/cloud-itonami-assoc-6810-jpn-recaj),
[`-6411-jpn-boj`](https://github.com/cloud-itonami/cloud-itonami-assoc-6411-jpn-boj),
[`-6120-usa-ctia`](https://github.com/cloud-itonami/cloud-itonami-assoc-6120-usa-ctia),
[`-5110-usa-a4a`](https://github.com/cloud-itonami/cloud-itonami-assoc-5110-usa-a4a),
[`-3510-usa-eei`](https://github.com/cloud-itonami/cloud-itonami-assoc-3510-usa-eei),
[`-2910-deu-vda`](https://github.com/cloud-itonami/cloud-itonami-assoc-2910-deu-vda),
[`-5510-usa-ahla`](https://github.com/cloud-itonami/cloud-itonami-assoc-5510-usa-ahla),
[`-2100-usa-phrma`](https://github.com/cloud-itonami/cloud-itonami-assoc-2100-usa-phrma),
[`-4719-usa-nrf`](https://github.com/cloud-itonami/cloud-itonami-assoc-4719-usa-nrf),
[`-4100-usa-agc`](https://github.com/cloud-itonami/cloud-itonami-assoc-4100-usa-agc),
[`-6020-usa-nab`](https://github.com/cloud-itonami/cloud-itonami-assoc-6020-usa-nab),
and
[`-3600-usa-awwa`](https://github.com/cloud-itonami/cloud-itonami-assoc-3600-usa-awwa).
Part of the [`cloud-itonami`](https://github.com/cloud-itonami)
compliance-fact family (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`, in `com-junkawasaki/root`).

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on ATA's behalf.

Coverage is reported honestly through the bounded `association-facts` ABI: an
association not admitted by `association-covered?` has **no spec-basis**, full
stop — never fabricate one.

## Data

- `src/association_facts.kotoba` — the sole production catalog source.
- `schema/association-rule.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).

Both entries were directly WebFetch-verified against
[trucking.org](https://www.trucking.org/)'s own pages: **ATA: 90 Years
and Rolling** (states ATA's exact incorporation date, September 23,
1933) and **Safety** (ATA's official public policy positions on CSA,
ELDs, hours-of-service, drug/alcohol testing, and driver licensing —
introduces a new `:kind`, `:policy-position`, for an association's
public regulatory/legislative stance, distinct from a binding member
commitment or a governance profile page; no single adoption date is
stated for these positions, so `:association-rule/established-date` is
deliberately omitted rather than fabricated).

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Policy text
itself remains ATA's; this repo stores only citation metadata
(id/title/url/dates), not full text.
