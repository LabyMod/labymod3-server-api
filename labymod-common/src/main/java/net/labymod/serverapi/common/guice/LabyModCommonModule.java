package net.labymod.serverapi.common.guice;

import com.google.inject.TypeLiteral;
import net.labymod.serverapi.api.emote.Emote;
import net.labymod.serverapi.api.emote.EmoteTransmitter;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.labychat.PlayingGameMode;
import net.labymod.serverapi.api.payload.PayloadBuffer;
import net.labymod.serverapi.api.permission.Permissible;
import net.labymod.serverapi.api.permission.PermissionService;
import net.labymod.serverapi.api.protocol.ChunkCachingProtocol;
import net.labymod.serverapi.api.protocol.ShadowProtocol;
import net.labymod.serverapi.api.protocol.chunkcaching.ChunkPosition;
import net.labymod.serverapi.api.serverinteraction.CineScopes;
import net.labymod.serverapi.api.serverinteraction.ServerSwitcher;
import net.labymod.serverapi.api.serverinteraction.Watermark;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuEntry;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;
import net.labymod.serverapi.api.sticker.Sticker;
import net.labymod.serverapi.api.sticker.StickerTransmitter;
import net.labymod.serverapi.common.emote.DefaultEmote;
import net.labymod.serverapi.common.emote.DefaultEmoteTransmitter;
import net.labymod.serverapi.common.extension.DefaultAddonExtension;
import net.labymod.serverapi.common.extension.DefaultAddonExtensionCollector;
import net.labymod.serverapi.common.extension.DefaultModificationExtension;
import net.labymod.serverapi.common.extension.DefaultModificationExtensionCollector;
import net.labymod.serverapi.common.extension.DefaultPackageExtension;
import net.labymod.serverapi.common.extension.DefaultPackageExtensionCollector;
import net.labymod.serverapi.common.labychat.DefaultPlayingGameMode;
import net.labymod.serverapi.common.payload.DefaultPayloadBuffer;
import net.labymod.serverapi.common.permission.DefaultPermission;
import net.labymod.serverapi.common.permission.DefaultPermissionService;
import net.labymod.serverapi.common.protocol.DefaultChunkCachingProtocol;
import net.labymod.serverapi.common.protocol.DefaultShadowProtocol;
import net.labymod.serverapi.common.protocol.chunkcaching.DefaultChunkPosition;
import net.labymod.serverapi.common.serverinteraction.DefaultCineScopes;
import net.labymod.serverapi.common.serverinteraction.DefaultServerSwitcher;
import net.labymod.serverapi.common.serverinteraction.DefaultWatermark;
import net.labymod.serverapi.common.serverinteraction.actionmenu.DefaultMenuEntry;
import net.labymod.serverapi.common.serverinteraction.subtitle.DefaultSubTitle;
import net.labymod.serverapi.common.serverinteraction.subtitle.DefaultSubTitleTransmitter;
import net.labymod.serverapi.common.sticker.DefaultSticker;
import net.labymod.serverapi.common.sticker.DefaultStickerTransmitter;

public class LabyModCommonModule extends LabyModAbstractModule {

  @Override
  protected void configure() {
    this.bind(EmoteTransmitter.class, DefaultEmoteTransmitter.class);
    this.bind(
        new TypeLiteral<ExtensionCollector<AddonExtension>>() {},
        DefaultAddonExtensionCollector.class);
    this.bind(
        new TypeLiteral<ExtensionCollector<ModificationExtension>>() {},
        DefaultModificationExtensionCollector.class);

    this.bind(
        new TypeLiteral<ExtensionCollector<PackageExtension>>() {},
        DefaultPackageExtensionCollector.class);

    this.bind(PlayingGameMode.class, DefaultPlayingGameMode.class);

    this.bind(PermissionService.class, DefaultPermissionService.class);

    this.bind(Watermark.class, DefaultWatermark.class);
    this.bind(ServerSwitcher.class, DefaultServerSwitcher.class);
    this.bind(CineScopes.class, DefaultCineScopes.class);

    // Install the factory for emotes
    this.installFactory(Emote.class, DefaultEmote.class, Emote.Factory.class);

    // Install the factory for addons
    this.installFactory(
        AddonExtension.class, DefaultAddonExtension.class, AddonExtension.Factory.class);

    // Install the factory for modifications
    this.installFactory(
        ModificationExtension.class,
        DefaultModificationExtension.class,
        ModificationExtension.Factory.class);

    // Install the factory for packages.
    this.installFactory(
        PackageExtension.class, DefaultPackageExtension.class, PackageExtension.Factory.class);

    // Install the factory for shadow protocol
    this.installFactory(
        ShadowProtocol.class, DefaultShadowProtocol.class, ShadowProtocol.Factory.class);

    // Install the factory for chunk caching protocol
    this.installFactory(
        ChunkCachingProtocol.class,
        DefaultChunkCachingProtocol.class,
        ChunkCachingProtocol.Factory.class);

    // Install the factory for Payload Communicator
    this.installFactory(
        PayloadBuffer.class, DefaultPayloadBuffer.class, PayloadBuffer.Factory.class);

    // Install the factory for permission
    this.installFactory(Permissible.class, DefaultPermission.class, Permissible.Factory.class);

    // Install the factory menu entries
    this.installFactory(MenuEntry.class, DefaultMenuEntry.class, MenuEntry.Factory.class);

    this.bind(StickerTransmitter.class, DefaultStickerTransmitter.class);
    // Install the factory for stickers
    this.installFactory(Sticker.class, DefaultSticker.class, Sticker.Factory.class);

    this.bind(SubTitleTransmitter.class, DefaultSubTitleTransmitter.class);
    this.installFactory(SubTitle.class, DefaultSubTitle.class, SubTitle.Factory.class);

    this.installFactory(
        ChunkPosition.class, DefaultChunkPosition.class, ChunkPosition.Factory.class);
  }
}
