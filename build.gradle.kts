plugins {
    kotlin("jvm") version "1.7.0"
    java
    id("maven-publish")
}

group = "dev.pravin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

fun getOpencvJarPath(): String {
    val local = "C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/opencv-3416.jar"
    val env = System.getenv("env") ?: "dev"
    return if(env == "CI") {
        "/home/runner/work/image-scanner/image-scanner/opencv-artifact/opencv_3416.jar"
    }else {
        local
    }
}

fun getOpencvBinaryPath(): String {
    val local = "C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/x64"
    val env = System.getenv("env") ?: "dev"
    return if(env == "CI") {
        "/home/runner/work/image-scanner/image-scanner/opencv-artifact/libopencv_java3416.so"
    }else {
        local
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(files(getOpencvJarPath()))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("io.mockk:mockk:1.12.7")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    jvmArgs("-Djava.library.path=${getOpencvBinaryPath()}")
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "$group"
                artifactId = rootProject.name
                version = version
                artifact("$buildDir/libs/${rootProject.name}-$version.jar")
            }
        }
    }

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
}
