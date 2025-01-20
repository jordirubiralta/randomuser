plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.jordirubiralta.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Network Dependencies
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.okhttp3)
    implementation(libs.gson)

    // Hilt Dependencies
    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.dagger.hilt.compiler)

    // Project Dependencies
    implementation(project(":domain"))
}
