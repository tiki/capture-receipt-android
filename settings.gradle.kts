pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {  url = uri("https://maven.microblink.com") }
    }
}

rootProject.name = "Capture Receipt"
include(":CaptureReceipt")
include(":CaptureReceipt:example")
 