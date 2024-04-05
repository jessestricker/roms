@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.clikt)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)
        }
    }
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "de.jessestricker.roms.AppKt"
}
