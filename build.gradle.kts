plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "ir.mahdi-tavakoli"
version = "2.0.0"

repositories {
    mavenCentral()
}


// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/d
//
// ocs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IC") // Target IDE Platform
    // Require the Android plugin (Gradle will choose the correct version):
    plugins.set(listOf("android"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChainFile.set(file("/home/mahditavakoli/Documents/ShelveMe-sign/chain.crt"))
        privateKeyFile.set(file("/home/mahditavakoli/Documents/ShelveMe-sign/private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

/*    runIde {
        // for macos
        // ideDir.set(file("/Applications/Android Studio.app/Contents"))

        // for ubuntu
        ideDir.set(file("/home/mahditavakoli/.local/share/JetBrains/Toolbox/apps/android-studio"))
    }*/

}
