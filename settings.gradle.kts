rootProject.name = "labymod-server-api"

fun defineModule(path: String) {
    include(path)
    findProject(":$path")?.name = path.replace(":", "-")
}

// Server API
defineModule("labymod-api")
defineModule("labymod-common")

// Proxies
defineModule("proxy:velocity")
defineModule("proxy:bungeecord")

// Servers
defineModule("server:bukkit")
defineModule("server:minestom")
defineModule("server:sponge")