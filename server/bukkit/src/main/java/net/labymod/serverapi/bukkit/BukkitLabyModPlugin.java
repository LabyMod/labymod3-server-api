package net.labymod.serverapi.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import net.jpountz.xxhash.XXHashFactory;
import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkCaching;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkHandle;
import net.labymod.serverapi.bukkit.guice.LabyModBukkitModule;
import net.labymod.serverapi.bukkit.protocol.chunkcaching.BukkitChunkHandleLegacy;
import net.labymod.serverapi.bukkit.util.ReflectionHelper;
import net.labymod.serverapi.common.guice.LabyModInjector;
import net.labymod.serverapi.common.payload.DefaultLegacyLabyModPayloadChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
public class BukkitLabyModPlugin extends JavaPlugin {

  private final LabyModInjector labyModInjector;

  public BukkitLabyModPlugin() {
    this.labyModInjector = LabyModInjector.getInstance();
    this.labyModInjector.addModule(
        new AbstractModule() {
          @Override
          protected void configure() {
            this.bind(JavaPlugin.class).toInstance(BukkitLabyModPlugin.this);
            this.bind(BukkitLabyModPlugin.class).toInstance(BukkitLabyModPlugin.this);
            this.bind(XXHashFactory.class).toInstance(XXHashFactory.fastestInstance());

            if (!ReflectionHelper.getInstance().getVersion().equalsIgnoreCase("v1_8_R3")) {
              this.bind(new TypeLiteral<ChunkHandle<Player, PacketContainer, PacketType>>() {})
                  .to(BukkitChunkHandleLegacy.class);
            }
          }
        });

    this.labyModInjector.addModule(new LabyModBukkitModule());
  }

  @Override
  public void onEnable() {
    PayloadChannelRegistrar<String> payloadChannelRegistrar =
        this.labyModInjector.getInjectedInstance(
            new TypeLiteral<PayloadChannelRegistrar<String>>() {});
    payloadChannelRegistrar.registerModernLegacyChannelIdentifier("LMC");
    payloadChannelRegistrar.registerModernChannelIdentifier("labymod", "main");

    LabyModInjector.getInstance().getInjectedInstance(new TypeLiteral<ChunkCaching<Player, PacketContainer>>() {});

    this.getServer()
        .getPluginManager()
        .registerEvents(
            (Listener)
                this.labyModInjector.getInjectedInstance(
                    new TypeLiteral<ConnectionService<Player>>() {}),
            this);
    this.getServer()
        .getPluginManager()
        .registerEvents(
            (Listener)
                this.labyModInjector.getInjectedInstance(DefaultLegacyLabyModPayloadChannel.class),
            this);
  }

  @Override
  public void onDisable() {}
}
