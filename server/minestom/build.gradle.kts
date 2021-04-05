plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


repositories {
    mavenCentral();
    jcenter();

    maven {
        name = "sponge-repository"
        url = uri("http://repo.spongepowered.org/maven")
    }


    maven {
        name = "minecraft-repository"
        url = uri("https://libraries.minecraft.net")
    }


    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    api(project(":labymod-common"))
    api("com.github.Minestom", "Minestom","1f2e4de0b3")
}