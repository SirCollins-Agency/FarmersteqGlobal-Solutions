#include "farm_core.h"

#include <cassert>
#include <cmath>
#include <iostream>

int main() {
    using farmsteq::core::FieldSnapshot;
    using farmsteq::core::estimateYieldKg;
    using farmsteq::core::focusScore;
    using farmsteq::core::recommendation;
    using farmsteq::core::waterNeedLitres;

    assert(std::abs(estimateYieldKg(1.0, 100.0, 1.0) - 1800.0) < 0.5);
    assert(estimateYieldKg(0.0, 100.0, 1.0) == 0.0);
    assert(std::abs(waterNeedLitres(1.0, 4.0, 1.0) - 40000.0) < 0.5);

    FieldSnapshot snap{"Maize", 2, 50, 0, 1.0};
    assert(focusScore(snap) > 0);
    const std::string advice = recommendation(snap);
    assert(!advice.empty());
    assert(advice.front() != ' ');

    std::cout << "Farmerssteq C++ core: all deterministic checks passed\n";
    return 0;
}
