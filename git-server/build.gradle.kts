plugins {
    application
    java
    id("kotlin-kapt")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.8.0"
}

group = "com.y4d"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit:junit:4.12")
    implementation("com.google.protobuf:protobuf-kotlin:3.22.2")
    implementation("io.netty:netty-all:4.1.10.Final")
    kapt("info.picocli:picocli-codegen:4.7.1")
    implementation("info.picocli:picocli:4.7.1")
    implementation(project(mapOf("path" to ":")))
}
kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}

application {
    val name = "com.y4d.git.server.GitCommandLine"
    mainClass.set(name)
}