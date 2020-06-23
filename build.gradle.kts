import io.kotless.plugin.gradle.dsl.kotless

plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("io.kotless") version "0.1.3" apply true
}

group = "com.escapesanctuary.kotless.example"
version = "1.0-SNAPSHOT"
var stage = "dev"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kotless", "ktor-lang", "0.1.5")
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

kotless {

    config {
        bucket = "escapesanctuary.kotless.com"
        prefix = stage

        terraform {
            profile = "default"
            region = "ap-northeast-1"
        }
    }

    webapp {
        lambda {
            memoryMb = 1024
            timeoutSec = 120
            kotless {
                packages = setOf("com.escapesanctuary.kotless.example")
            }
        }
        deployment {
            version = stage
        }
    }

    extensions {
        local {
            useAWSEmulation = true
        }
    }
}

tasks {
    register("deployProd") {
        dependsOn("deploy")
        doFirst { stage = "prod" }
    }
}
