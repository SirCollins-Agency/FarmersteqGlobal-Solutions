#pragma once

#include <string>

namespace farmsteq {
namespace core {

struct FieldSnapshot {
    std::string crop;
    int stage = 0;       // 0 seedling, 1 vegetative, 2 flowering, 3 mature
    int condition = 70;  // 0..100 farmer-observed health
    int daysSince = 0;   // days since last observation of this crop
    double areaHa = 1.0;
};

// Pure, deterministic functions. No clock, no locale, no I/O.
double estimateYieldKg(double areaHa, double healthIndex, double cropFactor);
double waterNeedLitres(double areaHa, double evapotranspirationMm, double cropFactor);
int focusScore(const FieldSnapshot& snapshot);
std::string recommendation(const FieldSnapshot& snapshot);

}  // namespace core
}  // namespace farmsteq
