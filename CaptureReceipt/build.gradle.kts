buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mytiki.capture_receipt"
    compileSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes += listOf("META-INF/LICENSE-notice.md", "META-INF/LICENSE.md", "META-INF/NOTICE.md")
        }
    }
}

dependencies {
    val kotlinVersion = if (project.hasProperty("kotlinVersion")) rootProject.ext["kotlinVersion"] as String else "1.8.22"
    val mockkVersion = if (project.hasProperty("mockkVersion")) rootProject.ext["mockkVersion"] as String else "1.13.5"
    val junitVersion = if (project.hasProperty("junitVersion")) rootProject.ext["junitVersion"] as String else "4.13.2"
    val coroutinesVersion = if (project.hasProperty("coroutinesVersion")) rootProject.ext["coroutinesVersion"] as String else "1.7.2"
    val androidxAppCompatVersion = if (project.hasProperty("androidxAppCompatVersion")) rootProject.ext["androidxAppCompatVersion"] as String else "1.6.1"
    val androidxJunitVersion = if (project.hasProperty("androidxJunitVersion")) rootProject.ext["androidxJunitVersion"] as String else "1.1.5"
    val androidxEspressoCoreVersion = if (project.hasProperty("androidxEspressoCoreVersion")) rootProject.ext["androidxEspressoCoreVersion"] as String else "3.5.1"
    val kotlinxVersion = if (project.hasProperty("kotlinxVersion")) rootProject.ext["kotlinxVersion"] as String else "1.7.2"
    val androidxCoreVersion = if (project.hasProperty("androidxCoreVersion")) rootProject.ext["androidxCoreVersion"] as String else "1.12.0"
    val androidxLifecycleVersion = if (project.hasProperty("androidxLifecycleVersion")) rootProject.ext["androidxLifecycleVersion"] as String else "2.6.2"
    val androidxWorkVersion = if (project.hasProperty("androidxWorkVersion")) rootProject.ext["androidxWorkVersion"] as String else "2.8.1"
    val sunMailVersion = if (project.hasProperty("sunMailVersion")) rootProject.ext["sunMailVersion"] as String else "1.6.7"
    val retrofitVersion = if (project.hasProperty("retrofitVersion")) rootProject.ext["retrofitVersion"] as String else "2.9.0"

    implementation("com.mytiki:tiki-sdk-android:2.2.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.appcompat:appcompat:$androidxAppCompatVersion")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.core:core:$androidxCoreVersion")
    implementation ("androidx.core:core-ktx:$androidxCoreVersion")
    implementation ("androidx.webkit:webkit:1.8.0")
    implementation ("androidx.work:work-runtime:$androidxWorkVersion")
    implementation ("androidx.work:work-runtime-ktx:$androidxWorkVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidxLifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$androidxLifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-common-java8:$androidxLifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$androidxLifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:$androidxLifecycleVersion")
    implementation ("androidx.fragment:fragment-ktx:1.6.1")
    implementation ("androidx.databinding:viewbinding:8.1.1")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
    implementation ("com.squareup.okio:okio:3.3.0")

    implementation ("com.google.android.gms:play-services-tasks:18.0.2")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.android.material:material:1.9.0")

    implementation ("com.jakewharton.timber:timber:5.0.1")

    implementation ("com.sun.mail:android-mail:$sunMailVersion")
    implementation ("com.sun.mail:android-activation:$sunMailVersion")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinxVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$kotlinxVersion")

    implementation ("androidx.test:monitor:1.6.1")
    implementation ("androidx.test.ext:junit-ktx:1.1.5")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation ("io.mockk:mockk:$mockkVersion")
    testImplementation ("org.json:json:20230227")

    testImplementation ("org.json:json:20230227")
    testImplementation ("junit:junit:$junitVersion")
    testImplementation ("io.mockk:mockk:$mockkVersion")

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
