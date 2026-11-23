# v0.0.1 ships unminified. Keep JNI entry points and serialization models stable
# for when minification is enabled in a later release.
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class com.farmsteq.global.data.** { *; }
-keep class kotlinx.serialization.json.** { *; }
