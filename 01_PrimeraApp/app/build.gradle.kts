plugins {
    // alias(libs.plugins.androidApplication) apply false
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.a01primeraapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.a01primeraapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    dependencies {
        // ...

        // Import the Firebase BoM
        implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

        // When using the BoM, you don't specify versions in Firebase library dependencies

        // Add the dependency for the Firebase SDK for Google Analytics
        implementation("com.google.firebase:firebase-analytics-ktx")

        // TODO: Add the dependencies for any other Firebase products you want to use
        // See https://firebase.google.com/docs/android/setup#available-libraries
        // For example, add the dependencies for Firebase Authentication and Cloud Firestore
        implementation("com.google.firebase:firebase-auth-ktx")
        implementation("com.google.firebase:firebase-firestore-ktx")
    }
}