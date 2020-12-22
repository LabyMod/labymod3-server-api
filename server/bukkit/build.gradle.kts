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

    maven {
        name = "dmulloy2"
        url = uri("https://repo.dmulloy2.net/nexus/repository/public/")
    }

    maven {
        name = "via-version"
        url = uri("https://repo.viaversion.com/")
    }


}

dependencies {
    api(project(":labymod-common"))
    api("org.spigotmc", "spigot-api", "1.12.2-R0.1-SNAPSHOT")
    api("com.comphenix.protocol", "ProtocolLib", "4.5.0")
    api("us.myles", "viaversion", "3.2.0")
    api("org.lz4", "lz4-java", "1.4.1")
}