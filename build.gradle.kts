import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:2.0.26")
    implementation("com.google.protobuf:protobuf-kotlin:3.22.2")
    implementation("io.netty:netty-all:4.1.10.Final")
    implementation("info.picocli:picocli-codegen:4.7.1")
    implementation(kotlin("stdlib-jdk8"))// https://mvnrepository.com/artifact/junit/junit
    testImplementation("junit:junit:4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
