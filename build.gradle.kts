import java.util.*
import java.io.FileInputStream

plugins {
    kotlin("jvm") version "1.7.0"
    java
    id("maven-publish")
}

group = "dev.pravin"
version = "1.0.1"

repositories {
    mavenCentral()
}

fun getOpencvJarPath(): String {
    val local = "C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/opencv-3416.jar"
    val env = System.getenv("ENVIRONMENT") ?: "dev"

    return if(env == "CI") {
        "/home/runner/work/image-scanner/image-scanner/opencv-artifact/opencv_3416.jar"
    }else {
        local
    }
}

fun getOpencvNativePath(): String {
    val local = "C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/x64"
    val env = System.getenv("ENVIRONMENT") ?: "dev"

    return if(env == "CI") {
        "/home/runner/work/image-scanner/image-scanner/opencv-artifact/libopencv_java3416.so"
    }else {
        local
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(files(getOpencvJarPath()))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("io.mockk:mockk:1.12.7")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    jvmArgs("-Djava.library.path=${getOpencvNativePath()}")
    useJUnitPlatform()
}

val githubPublishProperties = Properties()
githubPublishProperties.load(FileInputStream(rootProject.file("githubPublish.properties")))

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/pravinyo/image-scanner")
            credentials {
                username = System.getenv("GPR_USER")
                password = System.getenv("GPR_API_KEY")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            run {
                groupId = "$group"
                artifactId = rootProject.name
                version = version
//                artifact( "$buildDir/libs/${rootProject.name}-$version.jar")
            }
        }
    }
}