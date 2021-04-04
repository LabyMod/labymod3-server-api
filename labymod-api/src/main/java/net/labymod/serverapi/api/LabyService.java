package net.labymod.serverapi.api;

import net.labymod.serverapi.api.connection.ConnectionService;
import net.labymod.serverapi.api.discord.RichPresenceTransmitter;
import net.labymod.serverapi.api.emote.EmoteTransmitter;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.payload.PayloadChannelRegistrar;
import net.labymod.serverapi.api.payload.PayloadCommunicator;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.api.player.LabyModPlayerService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.api.serverinteraction.CineScopes;
import net.labymod.serverapi.api.serverinteraction.ServerSwitcher;
import net.labymod.serverapi.api.serverinteraction.TabListCache;
import net.labymod.serverapi.api.serverinteraction.Watermark;
import net.labymod.serverapi.api.serverinteraction.actionmenu.Menu;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplay;
import net.labymod.serverapi.api.serverinteraction.gui.InputPromptTransmitter;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;
import net.labymod.serverapi.api.sticker.Sticker;
import net.labymod.serverapi.api.sticker.StickerTransmitter;

public interface LabyService {

  CineScopes getCineScopes();

  <P> ConnectionService<P> getConnectionService();

  EconomyDisplay getEconomyDisplay();

  EmoteTransmitter getEmoteTransmitter();

  ExtensionCollector<AddonExtension> getAddonExtensionCollector();

  ExtensionCollector<ModificationExtension> getModificationExtensionCollector();

  ExtensionCollector<PackageExtension> getPackageExtensionCollector();

  ServerSwitcher getServerSwitcher();

  ChunkCachingProtocol.Factory getChunkCachingProtocolFactory();

  ShadowProtocol.Factory getShadowProtocolFactory();

  TabListCache getTabListCache();

  PermissionService getPermissionService();

  Menu getMenu();

  InputPromptTransmitter getInputPromptTransmitter();

  <P> LabyModPlayerService<P> getLabyPlayerService();

  PayloadCommunicator getPayloadCommunicator();

  RichPresenceTransmitter getRichPresenceTransmitter();

  SubTitle.Factory getSubTitleFactory();

  SubTitleTransmitter getSubTitleTransmitter();

  Sticker.Factory getStickerFactory();

  StickerTransmitter getStickerTransmitter();

  <P> PayloadChannelRegistrar<P> getPayloadChannelRegistrar();

  Watermark getWatermark();
}
