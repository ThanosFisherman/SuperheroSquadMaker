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
            buildConfigField("String", "BASE_URL", "\"https://dev-api.gwf.ch/\"")
        }
        getByName(BuildType.RELEASE) {
            buildConfigField("String", "BASE_URL", "\"https://api.gwf.ch/\"")
            //buildConfigField("String", "BASE_URL", "\"https://dev-api.gwf.ch/\"")
        }
    }
}

dependencies {
    addDataNetworkDependencies()
    addTestDependencies()
}
