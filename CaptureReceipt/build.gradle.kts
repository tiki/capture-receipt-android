buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("kotlin-kapt")
}

android {
    namespace = "com.mytiki.capture.receipt"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")


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
    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{NOTICE,LICENSE,DEPENDENCIES,LICENSE.md,NOTICE.txt,NOTICE.md}"
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    val mockkVersion =
        if (project.hasProperty("mockkVersion")) rootProject.ext["mockkVersion"] as String else "1.13.5"
    val junitVersion =
        if (project.hasProperty("junitVersion")) rootProject.ext["junitVersion"] as String else "4.13.2"
    val coroutinesVersion =
        if (project.hasProperty("coroutinesVersion")) rootProject.ext["coroutinesVersion"] as String else "1.7.2"

    implementation("androidx.compose.ui:ui-android:1.5.4")
    implementation("com.mytiki:tiki-sdk-android:2.2.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("androidx.test:monitor:1.6.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")

    testImplementation("junit:junit:$junitVersion")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}