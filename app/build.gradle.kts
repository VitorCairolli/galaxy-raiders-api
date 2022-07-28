import com.adarshr.gradle.testlogger.theme.ThemeType

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4.2/userguide/building_java_projects.html
 */

plugins {
  // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
  id("org.jetbrains.kotlin.jvm") version "1.6.20"

  // Apply the application plugin to add support for building a CLI application in Java.
  application

  // Apply the KTLint plugin to check and auto format Kotlin code.
  id("org.jmailen.kotlinter") version "3.10.0"

  // Apply the Detekt plugin to make static code analysis of Kotlin code.
  id("io.gitlab.arturbosch.detekt") version "1.20.0"

  // Apply the Test Logger plugin to print test results in the test task.
  id("com.adarshr.test-logger") version "3.2.0"

  // Apply the Shadow JAR Plugin to generate a standalone uber Jar.
  id("com.github.johnrengelman.shadow") version "7.1.2"

  // Apply the Kover plugin to better support Kotlin code coverage.
  id("org.jetbrains.kotlinx.kover") version "0.5.0"

  // Apply the Jacoco plugin to generate test coverage.
  jacoco
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

dependencies {
  // Align versions of all Kotlin components
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

  // Use the Kotlin JDK 8 standard library.
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  // Use Javalin to create a lightweight HTTP API.
  implementation("io.javalin:javalin:4.6.4")

  // Use SLF4J to setup a logger for Javalin.
  implementation("org.slf4j:slf4j-simple:1.7.36")

  // Use SLF4J to setup a logger for Javalin.
  implementation("org.slf4j:slf4j-simple:1.7.36")

  // Use Jackson as object serializer for Javalin.
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

  // Use the Kotlin JUnit 5 integration.
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.6.21")

  // Allow JUnit 5 parameterized tests
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
}

application {
  // Define the main class for the application.
  mainClass.set("galaxyraiders.AppKt")
}

// Configure the Manifest attributes to generate a JAR.
tasks.jar {
  manifest {
    attributes["Main-Class"] = "galaxyraiders.AppKt"
  }
}

// Set options for the Test Logger plugin.
// https://github.com/radarsh/gradle-test-logger-plugin#configuration
testlogger {
  theme = ThemeType.MOCHA
  showStandardStreams = true
}

// Enable support to JUnit Platform to execute JUnit 5 tests.
tasks.test {
  useJUnitPlatform()
  finalizedBy(tasks.jacocoTestReport)
}

// Require tests to run before generating the Jacoco test coverage report.
tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

// Configure the metadata to generate an uber JAR.
tasks.shadowJar {
  archiveBaseName.set("galaxy-raiders")
  archiveVersion.set("")
  archiveClassifier.set("")
}

// Configure Gradle to send its stdin to the program.
// https://stackoverflow.com/questions/13172137/console-application-with-java-and-gradle
tasks.getByName("runShadow", JavaExec::class) {
  standardInput = System.`in`
}