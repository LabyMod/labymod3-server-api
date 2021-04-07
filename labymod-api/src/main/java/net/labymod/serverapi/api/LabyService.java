package net.labymod.serverapi.api;

import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.discord.RichPresenceTransmitter;
import net.labymod.serverapi.api.emote.Emote;
import net.labymod.serverapi.api.emote.EmoteTransmitter;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.labychat.PlayingGameMode;
import net.labymod.serverapi.api.labychat.PlayingGameModeTransmitter;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.api.serverinteraction.CineScopes;
import net.labymod.serverapi.api.serverinteraction.CineScopesTransmitter;
import net.labymod.serverapi.api.serverinteraction.ServerSwitcher;
import net.labymod.serverapi.api.serverinteraction.ServerSwitcherTransmitter;
import net.labymod.serverapi.api.serverinteraction.TabListCache;
import net.labymod.serverapi.api.serverinteraction.TabListCacheTransmitter;
import net.labymod.serverapi.api.serverinteraction.Watermark;
import net.labymod.serverapi.api.serverinteraction.WatermarkTransmitter;
import net.labymod.serverapi.api.serverinteraction.actionmenu.Menu;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuTransmitter;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplay;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplayTransmitter;
import net.labymod.serverapi.api.serverinteraction.gui.InputPromptTransmitter;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;
import net.labymod.serverapi.api.sticker.Sticker;
import net.labymod.serverapi.api.sticker.StickerTransmitter;

/** This interface represents a service with all possible functions of the LabyMod. */
public interface LabyService {

  /**
   * Retrieves the protocol for the cine scopes feature.
   *
   * @return The cine scopes protocol.
   * @deprecated Use {@link #getCineScopesTransmitter()}
   */
  @Deprecated
  CineScopes getCineScopes();

  /**
   * Retrieves the transmitter for the cine scopes feature.
   *
   * @return The cine scope transmitter.
   */
  CineScopesTransmitter getCineScopesTransmitter();

  /**
   * Retrieves the connection service for LabyMod.
   *
   * @param <P> The player type of the specified platform.
   * @return The LabyMod connection service.
   */
  <P> ConnectionService<P> getConnectionService();

  /** @deprecated Use {@link #getEconomyDisplayTransmitter()} */
  @Deprecated
  EconomyDisplay getEconomyDisplay();

  /**
   * Retrieves the transmitter for the economy display feature.
   *
   * @return The economy display transmitter.
   */
  EconomyDisplayTransmitter getEconomyDisplayTransmitter();

  /**
   * Retrieves a factory for emotes.
   *
   * @return The emote factory.
   */
  Emote.Factory getEmoteFactory();

  /**
   * Retrieves the transmitter for the emote feature.
   *
   * @return The emote transmitter.
   */
  EmoteTransmitter getEmoteTransmitter();

  /**
   * Retrieves an {@link ExtensionCollector} specified for {@link AddonExtension}'s.
   *
   * @return The addon extension collector.
   */
  ExtensionCollector<AddonExtension> getAddonExtensionCollector();

  /**
   * Retrieves an {@link ExtensionCollector} specified for {@link ModificationExtension}'s.
   *
   * @return The modification extension collector.
   */
  ExtensionCollector<ModificationExtension> getModificationExtensionCollector();

  /**
   * Retrieves an {@link ExtensionCollector} specified for {@link PackageExtension}'s.
   *
   * @return The package extension collector.
   */
  ExtensionCollector<PackageExtension> getPackageExtensionCollector();

  /** @deprecated Use {@link #getServerSwitcherTransmitter()} */
  @Deprecated
  ServerSwitcher getServerSwitcher();

  /**
   * Retrieves the transmitter for the server switcher feature.
   *
   * @return The server switcher transmitter.
   */
  ServerSwitcherTransmitter getServerSwitcherTransmitter();

  /**
   * Retrieves a factory to create the chunk caching protocol.
   *
   * @return The chunk caching protocol factory.
   */
  ChunkCachingProtocol.Factory getChunkCachingProtocolFactory();

  /**
   * Retrieves a factory to create the shadow protocol.
   *
   * @return The shadow protocol factory.
   */
  ShadowProtocol.Factory getShadowProtocolFactory();

  /** @deprecated Use {@link #getTabListCacheTransmitter()} */
  @Deprecated
  TabListCache getTabListCache();

  /**
   * Retrieves the transmitter for the tab list cache feature.
   *
   * @return The tab list cache transmitter.
   */
  TabListCacheTransmitter getTabListCacheTransmitter();

  /**
   * Retrieves the permission service for LabyMod.
   *
   * @return The LabyMod permission service.
   */
  PermissionService getPermissionService();

  /** @deprecated Use {@link #getMenuTransmitter()} */
  @Deprecated
  Menu getMenu();

  /**
   * Retrieves the transmitter for the menu feature.
   *
   * @return The menu transmitter.
   */
  MenuTransmitter getMenuTransmitter();

  /**
   * Retrieves the transmitter to transmit the input prompt to the laby client.
   *
   * @return The transmitter to transmit the input prompt.
   */
  InputPromptTransmitter getInputPromptTransmitter();

  /**
   * Retrieves the player service for the LabyMod.
   *
   * @param <P> The player type of the specified platform.
   * @return The LabyMod player service.
   */
  <P> LabyModPlayerService<P> getLabyPlayerService();

  /** @deprecated Use {@link #getPlayingGameModeTransmitter()} */
  @Deprecated
  PlayingGameMode getPlayingGameMode();

  /**
   * Retrieves the transmitter for the playing game mode feature.
   *
   * @return The playing game mode transmitter.
   */
  PlayingGameModeTransmitter getPlayingGameModeTransmitter();

  PayloadCommunicator getPayloadCommunicator();

  /**
   * Retrieves the transmitter to transmit the rich presence to the laby client.
   *
   * @return The transmitter to transmit rich presence.
   */
  RichPresenceTransmitter getRichPresenceTransmitter();

  /**
   * Retrieves a factory for sub titles.
   *
   * @return The sub titles factory.
   */
  SubTitle.Factory getSubTitleFactory();

  /**
   * Retrieves the transmitter to transmit the sub titles to the laby client.
   *
   * @return The transmitter to transmit sub titles.
   */
  SubTitleTransmitter getSubTitleTransmitter();

  /**
   * Retrieves a factory for stickers.
   *
   * @return The sticker factory.
   */
  Sticker.Factory getStickerFactory();

  /**
   * Retrieves the transmitter to transmit the stickers to the laby client.
   *
   * @return The transmitter to transmit stickers.
   */
  StickerTransmitter getStickerTransmitter();

  /**
   * Retrieves the registrar for the LabyMod payload channels
   *
   * @param <P> The payload channel type.
   * @return The LabyMod payload channel registrar.
   */
  <P> PayloadChannelRegistrar<P> getPayloadChannelRegistrar();

  /**
   * Retrieves the protocol for watermark feature.
   *
   * @return The watermark protocol.
   * @deprecated Use {@link #getWatermarkTransmitter()}
   */
  @Deprecated
  Watermark getWatermark();

  /**
   * Retrieves the transmitter for the watermark feature.
   *
   * @return The watermark transmitter.
   */
  WatermarkTransmitter getWatermarkTransmitter();
}
