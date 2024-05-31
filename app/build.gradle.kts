plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shopsmart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shopsmart"
        minSdk = 27
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
    testOptions{
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
// Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    // Add the dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth:23.0.0")
    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.facebook.android:facebook-android-sdk:latest.release")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.9.0")
//    implementation("kr.co.prnd:readmore-textview:1.0.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("junit:junit:4.13.2")

    // Exclude protobuf-lite from espresso-contrib
    implementation("androidx.test.espresso:espresso-contrib:3.5.1") {
        exclude(group = "com.google.protobuf", module = "protobuf-lite")
    }
    implementation("androidx.test.espresso:espresso-intents:3.5.1")
    implementation("org.robolectric:robolectric:4.12.2")
    testImplementation ("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.test:monitor:1.6.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("at.blogc:expandabletextview:1.0.5")
    implementation("com.google.firebase:firebase-analytics:22.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:rules:1.5.0")
    implementation("androidx.test:monitor:1.6.1")
    testImplementation ("org.mockito:mockito-core:4.2.0")
    androidTestImplementation ("org.mockito:mockito-android:4.2.0")
    testImplementation ("org.mockito:mockito-inline:3.11.2")
    testImplementation ("androidx.test:runner:1.5.2")
    testImplementation ("org.robolectric:robolectric:4.12.2")


}