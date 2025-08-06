import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets

buildscript {
	repositories {
		mavenLocal()
		mavenCentral()
		maven { url = uri("https://repo.spring.io/plugins-release") }
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://plugins.gradle.org/m2/") }
	}

	val file = File(".env.properties")
	if (!file.exists()) {
		file.createNewFile()
	}
}

plugins {
	// dotenv
	id("co.uzzu.dotenv.gradle")

	war
	// Idea
	idea
	id("org.jetbrains.gradle.plugin.idea-ext")

	// Spring
	id("org.springframework.boot")
	id("io.spring.dependency-management")

	// Spring boot actuator generator
	id("com.gorylenko.gradle-git-properties") version "2.5.2"

	// Kotlin
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
	kotlin("plugin.allopen")
}

java {
	sourceCompatibility  = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
	jvmToolchain(21)
}

group = "com.ritense"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
	mavenLocal()
	maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
	maven { url = uri("https://repository.jboss.org/nexus/content/repositories/releases") }
	maven { url = uri("https://oss.sonatype.org/content/repositories/releases") }
	maven { url = uri("https://app.camunda.com/nexus/content/groups/public") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:7.21.0")

	implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")

	// Database
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.postgresql:postgresql:42.7.2")


	implementation("org.camunda.bpm.extension:camunda-platform-7-keycloak:7.21.6")
}

tasks.bootRun {
	environment.putAll(env.allVariables())
}
