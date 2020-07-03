import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id(GradlePluginId.DEPENDENCY_UPDATE) version GradlePluginVersion.GRADLE_VERSION_PLUGIN
}

buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
        //maven("https://maven.fabric.io/public")
    }
    dependencies {
        classpath(GradlePluginId.ANDROID_GRADLE_PLUGIN)
        classpath(GradlePluginId.KOTLIN_GRADLE_PLUGIN)
        classpath(GradlePluginId.SAFE_ARGS_GRADLE_PLUGIN)
        classpath(GradlePluginId.GOOGLE_PLAY_SERVICES_GRADLE_PLUGIN)
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}

tasks.withType<DependencyUpdatesTask> {

    // Reject all non stable versions
    rejectVersionIf {
        isNonStable(candidate.version)
    }

    // Disallow release candidates as upgradable versions from stable versions
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }

    // Using the full syntax
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }

    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
