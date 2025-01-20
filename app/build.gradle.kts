plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.jordirubiralta.randomuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jordirubiralta.randomuser"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {

    // Core and Utility Libraries
    implementation(libs.androidx.core.ktx)

    // Lifecycle and Activity
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // UI & Compose
    implementation(platform(libs.androidx.compose.bom)) // BOM for Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.navigation)

    // Hilt Dependencies
    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.dagger.hilt.compiler)

    // Unit and Android Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM for Compose in tests
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug Tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Project Dependencies
    implementation(project(":feature-users"))
    implementation(project(":domain"))
    implementation(project(":data"))
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
