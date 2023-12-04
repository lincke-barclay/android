// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.dagger.hilt.android") version "2.46" apply false
    id("com.google.firebase.appdistribution") version "4.0.1" apply false

    id("com.vanniktech.maven.publish") version "0.25.2" apply false // TODO - figure out how not to include this


    kotlin("jvm") version "1.9.0" apply false
    kotlin("plugin.serialization") version "1.9.0" apply false
    id("com.android.library") version "8.1.2" apply false
}