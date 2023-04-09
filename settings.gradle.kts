pluginManagement {
    repositories {
        maven {
            setUrl("https://maven.aliyun.com/repository/gradle-plugin")
        }
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "git-agent"
include("git-server")
include("git-cli")
