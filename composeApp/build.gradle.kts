@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
    // alias(libs.plugins.google.services) // Commented out until google-services.json is added
}

kotlin {
    androidTarget {
        compilations.all {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                // AndroidX Activity Compose
                implementation(libs.androidx.activity.compose)

                // Compose UI packaging from plugin (keep compose.* usage in common if needed)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)

                // Ktor Android
                implementation(libs.ktor.client.android)

                // Kotlinx Android
                implementation(libs.kotlinx.coroutines.android)

                // Firebase Messaging - Commented out until google-services.json is added
                // implementation("com.google.firebase:firebase-messaging-ktx:24.1.2")

                // WorkManager
                implementation(libs.androidx.work.runtime)

                // DataStore
                implementation(libs.androidx.datastore.preferences)
            }
        }

        val iosMain by creating {
            dependencies {
                // Ktor iOS
                implementation(libs.ktor.client.darwin)
            }
        }

        val commonMain by getting {
            dependencies {
                // Compose Multiplatform common runtime
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.ui)

                // Navigation Compose
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")

                // AndroidX lifecycle (ViewModel helpers)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)

                // Ktor common
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.auth)

                // Kotlinx
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "utt.equipo.hackathon"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "utt.equipo.hackathon"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
