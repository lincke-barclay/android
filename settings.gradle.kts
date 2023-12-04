pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "events"
include(":app")

/**
 * Compose Calendar Local Project
 */
includeBuild("../composecalendar")
//project(":composecalendar").projectDir = File("../composecalendar/composecalendar")

/**
 * Open Source Maps
 */
includeBuild("../osmdroid")
//project(":osmdroid").projectDir = File("../osmdroid")

/**
 * Compose Open Source Maps
 */
includeBuild("../osm-android-compose")
//project(":osm-android-compose").projectDir = File("../osm-android-compose")