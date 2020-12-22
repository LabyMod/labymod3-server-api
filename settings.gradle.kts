rootProject.name = "labymod-server-api"

// Server API
include("labymod-api")
include("labymod-common")

// Proxies
include("proxy:velocity")
include("proxy:bungeecord")

// Servers
include("server:bukkit")
include("server:minestom")
include("server:sponge")