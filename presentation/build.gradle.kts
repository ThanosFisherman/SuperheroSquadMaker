import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.SAFE_ARGS)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }
    kotlinOptions {
        // "this" is currently lacking a proper type
        // See: https://youtrack.jetbrains.com/issue/KT-31077
        val options = this as? KotlinJvmOptions
        options?.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    addPresentationDependencies()
    addTestDependencies()
}
