plugins {
    id("java-library")
}

group = "net.labymod"
version = properties["VERSION"] as String

repositories {
    mavenCentral()
    jcenter()

    maven {
        name = "velocity-repository"
        url = uri("https://repo.velocitypowered.com/snapshots/")
    }

    maven {
        name = "bungeecord-repository"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        name = "sponge-repository"
        url = uri("http://repo.spongepowered.org/maven")
    }

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

    api(project(":labymod-api"))
    api(project(":labymod-common"))
    api(project(":proxy:proxy-bungeecord"))
    api(project(":proxy:proxy-velocity"))
    api(project(":server:server-bukkit"))
    api(project(":server:server-sponge"))
}

val compileJava8Jar = task("compileJava8Jar", type = Jar::class) {

    configurations.all {
        exclude(group = "com.google.code.gson")
        exclude(group = "com.google.guava")
        exclude(group = "io.netty")
        exclude(group = "net.md-5")
        exclude(group = "com.velocitypowered")
        exclude(group = "org.spigotmc")
        exclude(group = "org.spongepowered")
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks.getByName("build") {
    dependsOn(compileJava8Jar)
}