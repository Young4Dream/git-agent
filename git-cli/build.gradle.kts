plugins {
    java
    id("kotlin-kapt")
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "com.y4d"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("com.google.protobuf:protobuf-kotlin:3.22.2")
    implementation("com.alibaba:fastjson:2.0.26")
    implementation("io.netty:netty-all:4.1.10.Final")
    implementation("info.picocli:picocli:4.7.1")
    kapt("info.picocli:picocli-codegen:4.7.1")
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(mapOf("path" to ":")))
    testImplementation("junit:junit:4.12")

}
kotlin {
    jvmToolchain(8)
}
kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}
//compileOptions {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    val name = "com.y4d.git.server.GitCommandLine"
    mainClass.set(name)
}