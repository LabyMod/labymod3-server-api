package net.labymod.serverapi.bukkit.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.labymod.serverapi.bukkit.payload.transmitter.exception.InvalidMinecraftServerVersionException;
import org.bukkit.Bukkit;

public final class ReflectionHelper {

  private static final ReflectionHelper instance = new ReflectionHelper();

  private String version;

  private ReflectionHelper() {
    String packageName = Bukkit.getServer().getClass().getPackage().getName();
    String[] packages = packageName.split("\\.");

    if (packages.length > 0) {
      String serverVersion = packages[packages.length - 1];
      if (!serverVersion.startsWith("v")) {
        throw new InvalidMinecraftServerVersionException(
            "The version for the running Minecraft server not found, suffix got: " + serverVersion);
      }
      this.version = serverVersion;
    }
  }

  public String getVersion() {
    return version;
  }

  public static ReflectionHelper getInstance() {
    return instance;
  }

  public Class<?> getNMSClass(String className) {
    return this.getClass("net.minecraft.server." + this.version + "." + className);
  }

  public Class<?> getOBCClass(String className) {
    return this.getClass("org.bukkit.craftbukkit." + this.version + "." + className);
  }

  public Class<?> getClass(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException exception) {
      throw new RuntimeException(String.format("Failed to find the %s class", name));
    }
  }

  public Constructor<?> getConstructor(Class<?> owner, Class<?>... parameters) {
    return this.getConstructor(owner, false, parameters);
  }

  public Constructor<?> getConstructor(Class<?> owner, boolean canThrown, Class<?>... parameters) {
    for (Constructor<?> constructor : owner.getDeclaredConstructors()) {
      if (Arrays.equals(constructor.getParameterTypes(), parameters)) {
        constructor.setAccessible(true);
        return constructor;
      }
    }

    if (canThrown) {
      throw new RuntimeException(
          String.format(
              "Failed to find the %s constructors with the given parameters: %s",
              owner.getName(), this.getParametersAsString(parameters)));
    }

    return null;
  }

  public Method getMethod(Class<?> owner, String methodName, Class<?>... parameters) {
    for (Method method : owner.getDeclaredMethods()) {
      if (method.getName().equals(methodName)) {
        if (parameters.length > 0) {
          if (Arrays.equals(method.getParameterTypes(), parameters)) {
            method.setAccessible(true);
            return method;
          }
        } else {
          method.setAccessible(true);
          return method;
        }
      }
    }

    throw new RuntimeException(
        String.format(
            "Failed to find %s#%s(%s)",
            owner.getName(), methodName, this.getParametersAsString(parameters)));
  }

  public Field getField(Class<?> owner, String fieldName) {
    for (Field field : owner.getDeclaredFields()) {
      if (field.getName().equals(fieldName)) {
        field.setAccessible(true);
        return field;
      }
    }

    throw new RuntimeException(
        String.format(
            "Failed to find the %s field of the owner class %s", fieldName, owner.getName()));
  }

  private String getParametersAsString(Class<?>... parameters) {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < parameters.length; i++) {
      Class<?> parameter = parameters[i];

      if (i == parameters.length - 1) {
        builder.append(parameter.getName());
        break;
      }

      builder.append(parameter.getName()).append(", ");
    }

    return builder.toString();
  }
}
