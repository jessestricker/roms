@file:Suppress("UnstableApiUsage")

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.kotlin)
    application

    alias(libs.plugins.detekt)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.clikt)
    implementation(libs.jackson.dataformat.xml)
    implementation(libs.jackson.module.kotlin)

    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockwebserver3.junit5)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit)
        }
    }
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "de.jessestricker.roms.AppKt"
}

detekt {
    basePath = rootProject.projectDir.absolutePath
}
tasks.withType<Detekt> {
    reports.sarif.required = true
}
