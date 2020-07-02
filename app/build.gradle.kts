import java.io.File
import java.util.*

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.GOOGLE_PLAY_SERVICES)
}

android {

    signingConfigs {

        create(BuildType.RELEASE) {
            val propertiesFile = project.rootProject.file("local.properties")
            if (propertiesFile.exists() && propertiesFile.canRead()) {
                val properties = Properties()
                properties.load(propertiesFile.inputStream())
                val keystoreFile = project.rootProject.file(rootDir.path + File.separator + properties.getProperty("keystore_name"))
                storeFile = keystoreFile
                storePassword = properties.getProperty("keystore_pass")
                keyAlias = properties.getProperty("key_alias")
                keyPassword = properties.getProperty("key_pass")
                isV2SigningEnabled = true
            } else {
                val keystoreFile = project.rootProject.file(rootDir.path + File.separator + System.getenv("keystore_name"))
                storeFile = keystoreFile
                storePassword = System.getenv("keystore_pass")
                keyAlias = System.getenv("key_alias")
                keyPassword = System.getenv("key_pass")
                isV2SigningEnabled = true
            }
        }
    }

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
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isShrinkResources = BuildTypeDebug.isShrinkResources
            manifestPlaceholders = BuildTypeDebug.manifestPlaceholders
            isDebuggable = BuildTypeDebug.isDebuggable
            signingConfig = signingConfigs.getByName(BuildType.DEBUG)
        }

        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isShrinkResources = BuildTypeRelease.isShrinkResources
            manifestPlaceholders = BuildTypeRelease.manifestPlaceholders
            isDebuggable = BuildTypeRelease.isDebuggable
            signingConfig = signingConfigs.getByName(BuildType.RELEASE)
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    addAppModuleDependencies()
    addTestDependencies()
}
