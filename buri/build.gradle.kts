

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.remmerw"
version = "0.0.6"

kotlin {


    androidLibrary {
        namespace = "io.github.remmerw.buri"
        compileSdk = 36
        minSdk = 27


        // Opt-in to enable and configure device-side (instrumented) tests
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
        }
    }


    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    linuxArm64()
    //wasmJs()
    //wasmWasi()
    //js()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.io.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}


mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "buri", version.toString())

    pom {
        name = "buri"
        description = "Bencode Library"
        inceptionYear = "2025"
        url = "https://github.com/remmerw/buri/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "remmerw"
                name = "Remmer Wilts"
                url = "https://github.com/remmerw/"
            }
        }
        scm {
            url = "https://github.com/remmerw/buri/"
            connection = "scm:git:git://github.com/remmerw/buri.git"
            developerConnection = "scm:git:ssh://git@github.com/remmerw/buri.git"
        }
    }
}
