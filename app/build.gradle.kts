plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.shivam.ottvideoplayer"
    compileSdk {
        version = release(37)
    }

    viewBinding {
        enable = true
    }

    defaultConfig {
        applicationId = "com.shivam.ottvideoplayer"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Navigation — XML
    implementation("androidx.navigation:navigation-fragment-ktx:2.10.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.10.2")

// Coroutines + Flow
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

// ViewModel + Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")

// Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // ExoPlayer playback engine
    implementation("androidx.media3:media3-exoplayer:1.11.1")

    // PlayerView and playback UI
    implementation("androidx.media3:media3-ui:1.11.1")
    implementation("com.google.code.gson:gson:2.10.1")

    // Common media interfaces and classes
}