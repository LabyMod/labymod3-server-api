plugins {
    id("java-library")
}

repositories {
    mavenCentral()
    maven {
        name = "bungeecord-repository"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        name = "spigot-repository"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    api(project(":labymod-common"))
    api("org.spigotmc", "spigot-api", "1.12.2-R0.1-SNAPSHOT")
}