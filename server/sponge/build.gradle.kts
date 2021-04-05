plugins {
    id("java-library")
}

repositories {
    mavenCentral()
    jcenter()

    maven {
        name = "sponge-repository"
        url = uri("http://repo.spongepowered.org/maven")
    }
}

dependencies {
    api(project(":labymod-common"))
    api("org.spongepowered", "spongeapi","7.2.0")
}