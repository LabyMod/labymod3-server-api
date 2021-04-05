plugins {
    id("org.spongepowered.plugin") version("0.9.0")
    id("java-library")
}

repositories {
    mavenCentral()
    maven {
        name = "velocity-repository"
        url = uri("https://repo.velocitypowered.com/snapshots/")
    }
}

dependencies {
    annotationProcessor("com.velocitypowered" ,"velocity-api", "1.1.0-SNAPSHOT")
    api(project(":labymod-common"))
    api("com.velocitypowered", "velocity-api", "1.1.0-SNAPSHOT")
}