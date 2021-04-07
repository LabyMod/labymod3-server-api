package net.labymod.serverapi.common;

import net.labymod.serverapi.api.LabyDebugger;
import net.labymod.serverapi.api.LabyService;
import net.labymod.serverapi.api.discord.RichPresenceTransmitter;
import net.labymod.serverapi.api.emote.Emote;
import net.labymod.serverapi.api.emote.EmoteTransmitter;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ExtensionCollector;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.api.labychat.PlayingGameMode;
import net.labymod.serverapi.api.labychat.PlayingGameModeTransmitter;
import net.labymod.serverapi.api.permission.PermissionService;
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
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuEntry;
import net.labymod.serverapi.api.serverinteraction.actionmenu.MenuTransmitter;
import net.labymod.serverapi.api.serverinteraction.addon.AddonRecommendation;
import net.labymod.serverapi.api.serverinteraction.addon.AddonRecommendationTransmitter;
import net.labymod.serverapi.api.serverinteraction.addon.RecommendedAddon;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplay;
import net.labymod.serverapi.api.serverinteraction.economy.EconomyDisplayTransmitter;
import net.labymod.serverapi.api.serverinteraction.gui.InputPromptTransmitter;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitle;
import net.labymod.serverapi.api.serverinteraction.subtile.SubTitleTransmitter;
import net.labymod.serverapi.api.sticker.Sticker;
import net.labymod.serverapi.api.sticker.Sticker.Factory;
import net.labymod.serverapi.api.sticker.StickerTransmitter;
import net.labymod.serverapi.common.discord.DefaultRichPresenceTransmitter;
import net.labymod.serverapi.common.emote.DefaultEmoteFactory;
import net.labymod.serverapi.common.emote.DefaultEmoteTransmitter;
import net.labymod.serverapi.common.extension.DefaultAddonExtensionCollector;
import net.labymod.serverapi.common.extension.DefaultModificationExtensionCollector;
import net.labymod.serverapi.common.extension.DefaultPackageExtensionCollector;
import net.labymod.serverapi.common.labychat.DefaultPlayingGameMode;
import net.labymod.serverapi.common.labychat.DefaultPlayingGameModeTransmitter;
import net.labymod.serverapi.common.permission.DefaultPermissionService;
import net.labymod.serverapi.common.protocol.DefaultChunkCachingProtocolFactory;
import net.labymod.serverapi.common.protocol.DefaultShadowProtocolFactory;
import net.labymod.serverapi.common.serverinteraction.DefaultCineScopes;
import net.labymod.serverapi.common.serverinteraction.DefaultCineScopesTransmitter;
import net.labymod.serverapi.common.serverinteraction.DefaultServerSwitcher;
import net.labymod.serverapi.common.serverinteraction.DefaultServerSwitcherTransmitter;
import net.labymod.serverapi.common.serverinteraction.DefaultTabListCache;
import net.labymod.serverapi.common.serverinteraction.DefaultTabListCacheTransmitter;
import net.labymod.serverapi.common.serverinteraction.DefaultWatermark;
import net.labymod.serverapi.common.serverinteraction.DefaultWatermarkTransmitter;
import net.labymod.serverapi.common.serverinteraction.actionmenu.DefaultMenu;
import net.labymod.serverapi.common.serverinteraction.actionmenu.DefaultMenuEntryFactory;
import net.labymod.serverapi.common.serverinteraction.actionmenu.DefaultMenuTransmitter;
import net.labymod.serverapi.common.serverinteraction.addon.DefaultAddonRecommendation;
import net.labymod.serverapi.common.serverinteraction.addon.DefaultAddonRecommendationTransmitter;
import net.labymod.serverapi.common.serverinteraction.addon.DefaultRecommendedAddonFactory;
import net.labymod.serverapi.common.serverinteraction.economy.DefaultEconomyDisplay;
import net.labymod.serverapi.common.serverinteraction.economy.DefaultEconomyDisplayTransmitter;
import net.labymod.serverapi.common.serverinteraction.gui.DefaultInputPromptTransmitter;
import net.labymod.serverapi.common.serverinteraction.subtitle.DefaultSubTitleFactory;
import net.labymod.serverapi.common.serverinteraction.subtitle.DefaultSubTitleTransmitter;
import net.labymod.serverapi.common.sticker.DefaultStickerFactory;
import net.labymod.serverapi.common.sticker.DefaultStickerTransmitter;

public abstract class AbstractLabyService implements LabyService {

  // Debugger
  private LabyDebugger labyDebugger;

  // Addon recommendation
  private AddonRecommendation addonRecommendation;
  private RecommendedAddon.Factory recommendedAddonFactory;
  private AddonRecommendationTransmitter addonRecommendationTransmitter;

  // Cinescopes
  private CineScopes cineScopes;
  private CineScopesTransmitter cineScopesTransmitter;

  // Laby Chat
  private PlayingGameMode playingGameMode;
  private PlayingGameModeTransmitter playingGameModeTransmitter;

  // Input prompt
  private InputPromptTransmitter inputPromptTransmitter;

  // Discord
  private RichPresenceTransmitter richPresenceTransmitter;

  // Permissions
  private PermissionService permissionService;

  // Economy display
  private EconomyDisplay economyDisplay;
  private EconomyDisplayTransmitter economyDisplayTransmitter;

  // Menu
  private Menu menu;
  private MenuEntry.Factory menuEntryFactory;
  private MenuTransmitter menuTransmitter;

  // Emotes
  private Emote.Factory emoteFactory;
  private EmoteTransmitter emoteTransmitter;

  // Extension collectors
  private ExtensionCollector<AddonExtension> addonExtensionCollector;
  private ExtensionCollector<ModificationExtension> modificationExtensionCollector;
  private ExtensionCollector<PackageExtension> packageExtensionCollector;

  // Special protocols
  private ChunkCachingProtocol.Factory chunkCachingProtocolFactory;
  private ShadowProtocol.Factory shadowProtocolFactory;

  // Server switcher
  private ServerSwitcher serverSwitcher;
  private ServerSwitcherTransmitter serverSwitcherTransmitter;

  // Sub titles
  private SubTitle.Factory subTitleFactory;
  private SubTitleTransmitter subTitleTransmitter;

  // Stickers
  private Sticker.Factory stickerFactory;
  private StickerTransmitter stickerTransmitter;

  // Tab list
  private TabListCache tabListCache;
  private TabListCacheTransmitter tabListCacheTransmitter;

  // Watermark
  private Watermark watermark;
  private WatermarkTransmitter watermarkTransmitter;

  protected void initialize() {
    this.labyDebugger = new DefaultLabyDebugger();

    // Addon recommendation
    this.addonRecommendation = new DefaultAddonRecommendation(this);
    this.recommendedAddonFactory = new DefaultRecommendedAddonFactory();
    this.addonRecommendationTransmitter = new DefaultAddonRecommendationTransmitter(this);

    // Cine scopes
    this.cineScopes = new DefaultCineScopes(this);
    this.cineScopesTransmitter = new DefaultCineScopesTransmitter(this);

    // Server switcher
    this.serverSwitcher = new DefaultServerSwitcher(this);
    this.serverSwitcherTransmitter = new DefaultServerSwitcherTransmitter(this);

    // Tab list cache
    this.tabListCache = new DefaultTabListCache(this);
    this.tabListCacheTransmitter = new DefaultTabListCacheTransmitter(this);

    // Watermark
    this.watermark = new DefaultWatermark(this);
    this.watermarkTransmitter = new DefaultWatermarkTransmitter(this);

    // Menu
    this.menu = new DefaultMenu(this);
    this.menuEntryFactory = new DefaultMenuEntryFactory();
    this.menuTransmitter = new DefaultMenuTransmitter(this);

    // Economy Display
    this.economyDisplay = new DefaultEconomyDisplay(this);
    this.economyDisplayTransmitter = new DefaultEconomyDisplayTransmitter(this);

    // Laby Chat
    this.playingGameMode = new DefaultPlayingGameMode(this);
    this.playingGameModeTransmitter = new DefaultPlayingGameModeTransmitter(this);

    // Input prompt
    this.inputPromptTransmitter = new DefaultInputPromptTransmitter(this);

    // Emotes
    this.emoteFactory = new DefaultEmoteFactory();
    this.emoteTransmitter = new DefaultEmoteTransmitter(this);

    // Discord
    this.richPresenceTransmitter = new DefaultRichPresenceTransmitter(this);

    // Permissions
    this.permissionService = new DefaultPermissionService(this);

    // Extension collectors
    this.addonExtensionCollector = new DefaultAddonExtensionCollector();
    this.modificationExtensionCollector = new DefaultModificationExtensionCollector();
    this.packageExtensionCollector = new DefaultPackageExtensionCollector();

    // Special protocols
    this.chunkCachingProtocolFactory = new DefaultChunkCachingProtocolFactory();
    this.shadowProtocolFactory = new DefaultShadowProtocolFactory();

    // Sub titles
    this.subTitleFactory = new DefaultSubTitleFactory();
    this.subTitleTransmitter = new DefaultSubTitleTransmitter(this);

    // Stickers
    this.stickerFactory = new DefaultStickerFactory();
    this.stickerTransmitter = new DefaultStickerTransmitter(this);
  }

  /** {@inheritDoc} */
  @Override
  public LabyDebugger getLabyDebugger() {
    return this.labyDebugger;
  }

  /** {@inheritDoc} */
  @Override
  public AddonRecommendation getAddonRecommendation() {
    return this.addonRecommendation;
  }

  /** {@inheritDoc} */
  @Override
  public RecommendedAddon.Factory getRecommendedAddonFactory() {
    return this.recommendedAddonFactory;
  }

  /** {@inheritDoc} */
  @Override
  public AddonRecommendationTransmitter getAddonRecommendationTransmitter() {
    return this.addonRecommendationTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public CineScopes getCineScopes() {
    return this.cineScopes;
  }

  /** {@inheritDoc} */
  @Override
  public CineScopesTransmitter getCineScopesTransmitter() {
    return this.cineScopesTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public RichPresenceTransmitter getRichPresenceTransmitter() {
    return this.richPresenceTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public Emote.Factory getEmoteFactory() {
    return this.emoteFactory;
  }

  /** {@inheritDoc} */
  @Override
  public EmoteTransmitter getEmoteTransmitter() {
    return this.emoteTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public ServerSwitcher getServerSwitcher() {
    return this.serverSwitcher;
  }

  /** {@inheritDoc} */
  @Override
  public ServerSwitcherTransmitter getServerSwitcherTransmitter() {
    return this.serverSwitcherTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public ExtensionCollector<AddonExtension> getAddonExtensionCollector() {
    return this.addonExtensionCollector;
  }

  /** {@inheritDoc} */
  @Override
  public ExtensionCollector<ModificationExtension> getModificationExtensionCollector() {
    return this.modificationExtensionCollector;
  }

  /** {@inheritDoc} */
  @Override
  public ExtensionCollector<PackageExtension> getPackageExtensionCollector() {
    return this.packageExtensionCollector;
  }

  /** {@inheritDoc} */
  @Override
  public ChunkCachingProtocol.Factory getChunkCachingProtocolFactory() {
    return this.chunkCachingProtocolFactory;
  }

  /** {@inheritDoc} */
  @Override
  public ShadowProtocol.Factory getShadowProtocolFactory() {
    return this.shadowProtocolFactory;
  }

  /** {@inheritDoc} */
  @Override
  public TabListCache getTabListCache() {
    return this.tabListCache;
  }

  /** {@inheritDoc} */
  @Override
  public TabListCacheTransmitter getTabListCacheTransmitter() {
    return this.tabListCacheTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public Watermark getWatermark() {
    return this.watermark;
  }

  /** {@inheritDoc} */
  @Override
  public WatermarkTransmitter getWatermarkTransmitter() {
    return this.watermarkTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public PermissionService getPermissionService() {
    return this.permissionService;
  }

  /** {@inheritDoc} */
  @Override
  public Menu getMenu() {
    return this.menu;
  }

  /** {@inheritDoc} */
  @Override
  public MenuTransmitter getMenuTransmitter() {
    return this.menuTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public MenuEntry.Factory getMenuEntryFactory() {
    return this.menuEntryFactory;
  }

  /** {@inheritDoc} */
  @Override
  public EconomyDisplay getEconomyDisplay() {
    return this.economyDisplay;
  }

  /** {@inheritDoc} */
  @Override
  public EconomyDisplayTransmitter getEconomyDisplayTransmitter() {
    return this.economyDisplayTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public InputPromptTransmitter getInputPromptTransmitter() {
    return this.inputPromptTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public PlayingGameMode getPlayingGameMode() {
    return this.playingGameMode;
  }

  /** {@inheritDoc} */
  @Override
  public PlayingGameModeTransmitter getPlayingGameModeTransmitter() {
    return playingGameModeTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public SubTitle.Factory getSubTitleFactory() {
    return this.subTitleFactory;
  }

  /** {@inheritDoc} */
  @Override
  public SubTitleTransmitter getSubTitleTransmitter() {
    return this.subTitleTransmitter;
  }

  /** {@inheritDoc} */
  @Override
  public Factory getStickerFactory() {
    return this.stickerFactory;
  }

  /** {@inheritDoc} */
  @Override
  public StickerTransmitter getStickerTransmitter() {
    return this.stickerTransmitter;
  }
}
