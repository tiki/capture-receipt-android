buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
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
    namespace = "com.mytiki.sdk.capture.receipt.capacitor"
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
    implementation("androidx.compose.ui:ui-android:1.5.4")
    val kotlinVersion =
        if (project.hasProperty("kotlinVersion")) rootProject.ext["kotlinVersion"] as String else "1.8.22"
    val mockkVersion =
        if (project.hasProperty("mockkVersion")) rootProject.ext["mockkVersion"] as String else "1.13.5"
    val junitVersion =
        if (project.hasProperty("junitVersion")) rootProject.ext["junitVersion"] as String else "4.13.2"
    val coroutinesVersion =
        if (project.hasProperty("coroutinesVersion")) rootProject.ext["coroutinesVersion"] as String else "1.7.2"
    val androidxAppCompatVersion =
        if (project.hasProperty("androidxAppCompatVersion")) rootProject.ext["androidxAppCompatVersion"] as String else "1.6.1"
    val androidxJunitVersion =
        if (project.hasProperty("androidxJunitVersion")) rootProject.ext["androidxJunitVersion"] as String else "1.1.5"
    val androidxEspressoCoreVersion =
        if (project.hasProperty("androidxEspressoCoreVersion")) rootProject.ext["androidxEspressoCoreVersion"] as String else "3.5.1"
    val kotlinxVersion =
        if (project.hasProperty("kotlinxVersion")) rootProject.ext["kotlinxVersion"] as String else "1.7.2"
    val androidxCoreVersion =
        if (project.hasProperty("androidxCoreVersion")) rootProject.ext["androidxCoreVersion"] as String else "1.12.0"
    val androidxLifecycleVersion =
        if (project.hasProperty("androidxLifecycleVersion")) rootProject.ext["androidxLifecycleVersion"] as String else "2.6.2"
    val androidxWorkVersion =
        if (project.hasProperty("androidxWorkVersion")) rootProject.ext["androidxWorkVersion"] as String else "2.8.1"
    val sunMailVersion =
        if (project.hasProperty("sunMailVersion")) rootProject.ext["sunMailVersion"] as String else "1.6.7"
    val retrofitVersion =
        if (project.hasProperty("retrofitVersion")) rootProject.ext["retrofitVersion"] as String else "2.9.0"

    implementation("com.mytiki:tiki-sdk-android:2.2.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("javax.inject:javax.inject:1")

    implementation("androidx.test:monitor:1.6.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.json:json:20230227")

    testImplementation("org.json:json:20230227")
    testImplementation("junit:junit:$junitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.microblink.blinkreceipt:blinkreceipt-bom:1.6.8"))
    implementation("com.microblink.blinkreceipt:blinkreceipt-account-linking")
    implementation("com.microblink.blinkreceipt:blinkreceipt-camera")
    implementation("com.microblink.blinkreceipt:blinkreceipt-camera-ui")
    implementation("com.microblink.blinkreceipt:blinkreceipt-core")
    implementation("com.microblink.blinkreceipt:blinkreceipt-digital")
    implementation("com.microblink.blinkreceipt:blinkreceipt-recognizer")
}