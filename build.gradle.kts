plugins {
    kotlin("jvm") version "1.7.0"
    java
}

group = "dev.pravin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(files("C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/opencv-3416.jar"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("io.mockk:mockk:1.12.7")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    jvmArgs("-Djava.library.path=C:/Users/tripa/Downloads/opencv-3.4.16-vc14_vc15/opencv/build/java/x64")
    useJUnitPlatform()
}