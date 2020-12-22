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
    api("io.netty", "netty-all", "4.1.54.Final")
    api("com.google.code.gson", "gson", "2.8.6")
    api("com.google.guava:guava:21.0")
    // This is one of the reasons why I don't want to support a 6 year old version anymore.
    // Thanks to the PvP community ♥
    api("com.google.inject", "guice", "4.0")
    api("com.google.inject.extensions", "guice-assistedinject", "4.0")
}