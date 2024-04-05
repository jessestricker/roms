@file:Suppress("UnstableApiUsage")

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.jvm)
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

detekt {
    basePath = rootProject.projectDir.absolutePath
}
tasks.withType<Detekt> {
    reports.sarif.required = true
}
