#!/usr/bin/env bash
# Host-side verification of the pure C++ core (no NDK required).
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
BIN="${TMPDIR:-/tmp}/farmsteq-core-test"

g++ -std=c++17 -Wall -Wextra \
  -I"$ROOT/app/src/main/cpp" \
  "$ROOT/app/src/main/cpp/farm_core.cpp" \
  "$ROOT/app/src/main/cpp/tests/core_test.cpp" \
  -o "$BIN"

"$BIN"
