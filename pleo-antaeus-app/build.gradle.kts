plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

kotlinProject()

dataLibs()

application {
    mainClassName = "io.pleo.antaeus.app.AntaeusApp"
}

dependencies {
    implementation(project(":pleo-antaeus-data"))
    implementation(project(":pleo-antaeus-rest"))
    implementation(project(":pleo-antaeus-core"))
    implementation(project(":pleo-antaeus-models"))
}

tasks {
    shadowJar {
        manifest {
            attributes("Main-Class" to "com.example.ApplicationKt")
        }
    }
}
