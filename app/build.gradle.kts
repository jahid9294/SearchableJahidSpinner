plugins {
    alias(libs.plugins.android.application)
    id("maven-publish")
}

android {
    namespace = "com.example.rahim"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.rahim"
        minSdk = 21
        targetSdk = 34
        versionCode = 4
        versionName = "4.0.0"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            // Specify the artifact manually for application modules
            artifact("$buildDir/outputs/apk/release/app-release.apk") {
                classifier = "release"
            }
            groupId = "com.example"
            artifactId = "rahim"
            version = "3.0.0"
        }
    }
    repositories {
        mavenLocal()
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
}
