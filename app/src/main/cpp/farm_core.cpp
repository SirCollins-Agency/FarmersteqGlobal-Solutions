#include "farm_core.h"

#include <algorithm>
#include <cmath>

namespace farmsteq {
namespace core {
namespace {

double clamp01(double value) {
    return std::max(0.0, std::min(1.0, value));
}

double stageFactor(int stage) {
    switch (stage) {
        case 0: return 0.45;  // seedling
        case 1: return 0.85;  // vegetative
        case 2: return 1.00;  // flowering - peak sensitivity
        case 3: return 0.70;  // mature
        default: return 0.60;
    }
}

double cropFactorFor(const std::string& crop) {
    if (crop == "Maize") return 1.05;
    if (crop == "Rice") return 1.25;
    if (crop == "Cassava") return 0.85;
    if (crop == "Yam") return 0.95;
    if (crop == "Sorghum") return 0.80;
    if (crop == "Groundnut") return 0.75;
    return 0.90;
}

}  // namespace

double estimateYieldKg(double areaHa, double healthIndex, double cropFactor) {
    const double area = std::max(0.0, areaHa);
    const double health = clamp01(healthIndex / 100.0);
    const double factor = std::max(0.1, cropFactor);
    // Baseline 1800 kg/ha: a conservative smallholder reference, not a promise.
    return std::round(1800.0 * area * factor * (0.35 + 0.65 * health));
}

double waterNeedLitres(double areaHa, double evapotranspirationMm, double cropFactor) {
    const double area = std::max(0.0, areaHa);
    const double eto = std::max(0.0, evapotranspirationMm);
    const double kc = std::max(0.1, cropFactor);
    // 1 mm over 1 hectare = 10,000 litres.
    return std::round(area * 10000.0 * eto * kc);
}

int focusScore(const FieldSnapshot& snapshot) {
    const int conditionPenalty = (100 - std::clamp(snapshot.condition, 0, 100)) / 2;
    const int staleness = std::min(snapshot.daysSince, 14) * 3;
    const int stageWeight = static_cast<int>(stageFactor(snapshot.stage) * 20);
    return std::clamp(stageWeight + conditionPenalty + staleness, 0, 100);
}

std::string recommendation(const FieldSnapshot& snapshot) {
    const double kc = cropFactorFor(snapshot.crop);
    if (snapshot.condition < 35) {
        return "Walk the worst-looking rows first. Check moisture at root depth before "
               "you apply anything, then log what you find.";
    }
    if (snapshot.daysSince >= 7) {
        return "It has been a while since this field was checked. Make one short pass "
               "today and record a fresh observation.";
    }
    if (snapshot.stage == 2 && snapshot.condition < 65) {
        return "Flowering is the sensitive stage: keep soil moisture steady and "
               "avoid heavy work on the crop today.";
    }
    if (snapshot.stage == 0) {
        return "Seedling stage: check for gaps and pest damage early. Small fixes now "
               "save a lot of work later.";
    }
    if (snapshot.stage == 3) {
        return "Crop is maturing. Check for lodging and plan your harvest window while "
               "the weather is favourable.";
    }
    double litres = waterNeedLitres(std::max(snapshot.areaHa, 0.25), 4.0, kc);
    if (litres > 12000.0) {
        return "Demand is high for this crop and area. Prioritise morning irrigation and "
               "mulch to reduce loss.";
    }
    return "Crop looks steady. Keep your routine pass and record any change you notice.";
}

}  // namespace core
}  // namespace farmsteq
