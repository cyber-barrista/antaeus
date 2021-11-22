import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.3.70" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

allprojects {
    group = "io.pleo"
    version = "1.0"

    plugins.apply("org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
        jcenter()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
