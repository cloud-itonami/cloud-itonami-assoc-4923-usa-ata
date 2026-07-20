# ADR 0001: Kotoba is the ATA catalog source authority

- Status: Accepted
- Date: 2026-07-21

## Decision

`src/association_facts.kotoba` is the sole production source. Both citations
retain every present scalar field. The founding citation retains its complete
`1933-09-23` date, while the safety policy positions continue to omit an
unverified date. Topic count plus indexed access preserves the governance and
safety topics. Unknown values and indexes return zero or typed option-none; no
effects are declared.

CI executes reference semantics, restricted JavaScript, instantiated typed
WebAssembly, and production source-authority checks. Clojure and the JVM are
compiler/test hosts only.

## Consequences

- Complete and missing dates retain their source precision.
- The catalog ABI is bounded, typed, deterministic, and fail-closed.
