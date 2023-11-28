plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.appdistribution")

    kotlin("plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "com.alth.events"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alth.events"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            firebaseAppDistribution {
                artifactType = "APK"
                releaseNotes = "Release"
                testers = "lincketheo@gmail.com adbarclay92@gmail.com"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.1.0") // Converts scalars (Strings Ints etc to serialized stuff)

    // Firebase
    // Auth
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")


    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.46")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    // Viewmodels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.3.0-alpha02")
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")

    // Room
    val room_version = "2.6.0"
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    // Pull refresh
    implementation("eu.bambooapps:compose-material3-pullrefresh:1.0.0")

    // Image cropper
    implementation("com.vanniktech:android-image-cropper:4.5.0")
}