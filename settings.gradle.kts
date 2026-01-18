@file:Suppress("UnstableApiUsage")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "roms"
include("app")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
