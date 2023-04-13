plugins {
    java
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.y4d"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/junit/junit
    testImplementation("junit:junit:4.12")
    implementation("com.google.protobuf:protobuf-kotlin:3.22.2")
    implementation("io.netty:netty-all:4.1.10.Final")
    implementation("info.picocli:picocli-codegen:4.7.1")
}