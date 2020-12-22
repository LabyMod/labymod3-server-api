package net.labymod.serverapi.bukkit.util;

import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

public class NetworkHelper {

  private static final NetworkHelper instance = new NetworkHelper();
  private final ReflectionHelper reflectionHelper;

  private Field channelField;
  private Field networkManagerField;
  private Field playerConnectionField;

  private Method playerGetHandleMethod;

  private NetworkHelper() {
    this.reflectionHelper = ReflectionHelper.getInstance();
  }

  public static NetworkHelper getInstance() {
    return instance;
  }

  public Channel getChannel(Player player) {
    return this.getChannel(this.getPlayerHandle(player));
  }

  private Object getPlayerHandle(Player player) {
    try {

      if (this.playerGetHandleMethod == null) {
        this.playerGetHandleMethod = reflectionHelper.getMethod(player.getClass(), "getHandle");
      }

      return this.playerGetHandleMethod.invoke(player);
    } catch (IllegalAccessException | InvocationTargetException exception) {
      return null;
    }
  }

  private Object getNetworkManager(Object player) {
    Object connection = this.getPlayerConnection(player);

    try {
      if (this.networkManagerField == null) {
        this.networkManagerField =
            reflectionHelper.getField(connection.getClass(), "networkManager");
      }

      return networkManagerField.get(connection);
    } catch (IllegalAccessException exception) {
      return null;
    }
  }

  private Channel getChannel(Object player) {
    Object networkManager = this.getNetworkManager(player);
    try {

      if (this.channelField == null) {
        this.channelField = reflectionHelper.getField(networkManager.getClass(), "channel");
      }

      return (Channel) channelField.get(networkManager);
    } catch (IllegalAccessException exception) {
      return null;
    }
  }

  private Object getPlayerConnection(Object player) {
    try {

      if (this.playerConnectionField == null) {
        this.playerConnectionField =
            reflectionHelper.getField(player.getClass(), "playerConnection");
      }
      return this.playerConnectionField.get(player);
    } catch (IllegalAccessException exception) {
      return null;
    }
  }
}
