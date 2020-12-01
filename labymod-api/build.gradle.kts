plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api("io.netty", "netty-buffer", "4.1.54.Final")
    api("com.google.code.gson", "gson", "2.8.6")
}