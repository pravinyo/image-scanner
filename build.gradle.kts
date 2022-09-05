plugins {
    kotlin("jvm") version "1.7.0"
    java
    id("maven-publish")
}

group = "dev.pravin"
version = "1.0.3"

repositories {
    mavenCentral()
}

fun getOpencvJarPath(): String {
    val local = "C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/opencv-3416.jar"

    return when (System.getenv("ENVIRONMENT") ?: "dev") {
        "CI" -> {
            "/home/runner/work/image-scanner/image-scanner/opencv-artifact/opencv_3416.jar"
        }
        "Windows-CI" -> {
            "D:\\a\\image-scanner\\image-scanner\\opencv\\build\\java\\opencv-3416.jar"
        }
        else -> {
            local
        }
    }
}

fun getOpencvNativePath(): String {
    val local = "C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/x64"

    return when (System.getenv("ENVIRONMENT") ?: "dev") {
        "CI" -> {
            "/home/runner/work/image-scanner/image-scanner/opencv-artifact/libopencv_java3416.so"
        }
        "Windows-CI" -> {
            "D:\\a\\image-scanner\\image-scanner\\opencv\\build\\java\\x64"
        }
        else -> {
            local
        }
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