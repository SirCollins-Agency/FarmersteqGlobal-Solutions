#include <jni.h>

#include <string>

#include "farm_core.h"

extern "C" {

JNIEXPORT jdouble JNICALL
Java_com_farmsteq_global_core_FarmCore_nativeEstimateYieldKg(
        JNIEnv*, jclass, jdouble areaHa, jdouble health, jdouble cropFactor) {
    return static_cast<jdouble>(
        farmsteq::core::estimateYieldKg(areaHa, health, cropFactor));
}

JNIEXPORT jdouble JNICALL
Java_com_farmsteq_global_core_FarmCore_nativeWaterNeedLitres(
        JNIEnv*, jclass, jdouble areaHa, jdouble etoMm, jdouble cropFactor) {
    return static_cast<jdouble>(
        farmsteq::core::waterNeedLitres(areaHa, etoMm, cropFactor));
}

JNIEXPORT jint JNICALL
Java_com_farmsteq_global_core_FarmCore_nativeFocusScore(
        JNIEnv*, jclass, jint stage, jint condition, jint daysSince) {
    farmsteq::core::FieldSnapshot snapshot;
    snapshot.stage = stage;
    snapshot.condition = condition;
    snapshot.daysSince = daysSince;
    return static_cast<jint>(farmsteq::core::focusScore(snapshot));
}

JNIEXPORT jstring JNICALL
Java_com_farmsteq_global_core_FarmCore_nativeRecommendation(
        JNIEnv* env, jclass, jstring crop, jint stage, jint condition,
        jint daysSince, jdouble areaHa) {
    const char* chars = env->GetStringUTFChars(crop, nullptr);
    if (chars == nullptr) {
        return env->NewStringUTF("Log an observation to get a recommendation.");
    }
    const std::string cropName(chars);
    env->ReleaseStringUTFChars(crop, chars);

    farmsteq::core::FieldSnapshot snapshot;
    snapshot.crop = cropName;
    snapshot.stage = stage;
    snapshot.condition = condition;
    snapshot.daysSince = daysSince;
    snapshot.areaHa = areaHa;

    const std::string result = farmsteq::core::recommendation(snapshot);
    return env->NewStringUTF(result.c_str());
}

}  // extern "C"
