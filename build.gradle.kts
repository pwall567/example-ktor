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
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    implementation("net.pwall.json:json-kotlin:2.1")
    implementation("net.pwall.json:json-ktor:0.7")
    implementation("net.pwall.json:json-ktor-client:0.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("com.tyro.oss:random-data:0.0.2")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
}

application {
    mainClassName = "com.example.mainKt"
}
