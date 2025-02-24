plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.notes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.notes"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true // Enable minification for release
            isShrinkResources = true // Shrink resources for smaller APK
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false // Explicitly disable for debug
        }
    }

    buildFeatures {
        viewBinding = true // Enable View Binding explicitly
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
    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2") // For Firestore RecyclerView adapters

    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Material Design Components
    implementation(libs.material) // Use libs version instead of direct version
    implementation("androidx.cardview:cardview:1.0.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // For coroutines

    // Additional useful libraries
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // For lifecycle management
    implementation("androidx.fragment:fragment-ktx:1.6.2") // For fragment support if needed
}