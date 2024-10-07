plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "uz.karsoft.baxt"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.karsoft.baxt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        //compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Views/Fragments Integration
    implementation (libs.androidx.navigation.fragment)
    implementation (libs.androidx.navigation.ui)

    // Feature module support for Fragments
    implementation (libs.androidx.navigation.dynamic.features.fragment)

    // Testing Navigation
    androidTestImplementation (libs.androidx.navigation.testing)

    implementation (libs.circleimageview)

    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.retrofit2.kotlin.coroutines.adapter)
    implementation (libs.logging.interceptor.v500alpha3)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.insert.koin.koin.android)
    // Jetpack WorkManager
    implementation(libs.koin.androidx.workmanager)
    // Navigation Graph
    implementation(libs.koin.androidx.navigation)

    implementation(libs.okhttp)

    implementation(libs.gson)

    /** logging interceptor*/
    implementation(libs.logging.interceptor)
    /** glide */
    implementation(libs.glide)

    /** swipe refresh layout */
    implementation(libs.androidx.swiperefreshlayout)

    /** json */
    implementation(libs.kotlinx.serialization.json)
}