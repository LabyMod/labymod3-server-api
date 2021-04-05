plugins {
    id("java-library")
}

repositories {
    mavenCentral()
    maven {
        name = "bungeecord-repository"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    api(project(":labymod-common"))
    api("net.md-5", "bungeecord-api", "1.16-R0.4-SNAPSHOT")
}