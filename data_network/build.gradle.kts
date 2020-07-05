plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
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

    buildTypes {
        getByName(BuildType.DEBUG) {
            buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com:443/v1/public/\"")
        }
        getByName(BuildType.RELEASE) {
            buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com:443/v1/public/\"")
        }
    }
}

dependencies {
    addDataNetworkDependencies()
    addTestDependencies()
}
