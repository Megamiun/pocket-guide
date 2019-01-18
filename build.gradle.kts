import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"
    kotlin("plugin.jpa") version "1.3.11"
    kotlin("plugin.spring") version "1.3.11"

    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.springframework.boot") version "2.1.2.RELEASE"
}

group = "br.com.gabryel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    runtime("com.h2database:h2:1.4.197")

    implementation(kotlin("stdlib-jdk8"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.hamcrest:hamcrest-core:2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.0-M1")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    gradleVersion = "5.1.1"
}