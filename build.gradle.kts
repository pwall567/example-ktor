/*
 * build.gradle.kts
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.example"
version = "0.1-SNAPSHOT"

val kotlinVersion = "1.3.50"
val ktorVersion = "1.2.4"
val mockkVersion = "1.9.3"

plugins {
    kotlin("jvm") version "1.3.50"
    application
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            languageVersion = "1.3"
            jvmTarget = "1.8"
        }
    }
}

repositories {
    jcenter()
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("net.pwall.json:json-kotlin:3.0")
    implementation("net.pwall.json:json-ktor:0.8")
    implementation("net.pwall.json:json-ktor-client:0.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("com.tngtech.archunit:archunit:0.13.0")
}

application {
    mainClassName = "com.example.mainKt"
}
