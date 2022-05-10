import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.veronica."
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	// Metrics
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	// Micrometer
	implementation("io.micrometer:micrometer-registry-prometheus")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
