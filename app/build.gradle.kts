import java.net.URI

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.david.superlist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.david.superlist"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }

    buildToolsVersion = "34.0.0"

}

dependencies {

    implementation("com.google.firebase:firebase-auth")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-database:20.0.3")
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    //Gráfico:
    implementation("com.github.PhilJay:MPAndroidChart:v3.0.3")

    //Dependencias PDF
    implementation ("com.itextpdf:itextg:5.5.10")
    
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
    implementation("com.google.android.material:material:1.12.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0-rc01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0-rc01")
    implementation("androidx.compose.ui:ui-graphics-android:1.5.4")
    implementation("androidx.mediarouter:mediarouter:1.6.0")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha02")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha02")
}