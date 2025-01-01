plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.mabiluygulamaproje"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mabiluygulamaproje"
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // YouTube Player bağımlılıkları
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    
    // Lifecycle bağımlılıkları
    implementation("androidx.lifecycle:lifecycle-runtime:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    implementation("androidx.lifecycle:lifecycle-process:2.6.2")
    
    // WebView bağımlılıkları
    implementation("androidx.webkit:webkit:1.8.0")
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}