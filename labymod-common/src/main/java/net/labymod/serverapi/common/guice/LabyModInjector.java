package net.labymod.serverapi.common.guice;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import java.util.Arrays;
import java.util.Set;

public class LabyModInjector {

  private static final LabyModInjector instance = new LabyModInjector();

  private final Set<Module> modules;
  private Injector injector;

  private LabyModInjector() {
    this.modules = Sets.newConcurrentHashSet();
    this.modules.add(new LabyModCommonModule());
  }

  public static LabyModInjector getInstance() {
    return instance;
  }

  public LabyModInjector addModule(Module module) {
    this.modules.add(module);
    return this;
  }

  public LabyModInjector addModules(Module... modules) {
    this.modules.addAll(Arrays.asList(modules));
    return this;
  }

  public <T> T getInjectedInstance(Key<T> injectedInstance) {
    return this.getInjector().getInstance(injectedInstance);
  }

  public <T> T getInjectedInstance(TypeLiteral<T> injectedInstance) {
    return this.getInjector().getInstance(Key.get(injectedInstance));
  }

  public <T> T getInjectedInstance(Class<T> injectedInstance) {
    return this.getInjector().getInstance(injectedInstance);
  }

  private Injector getInjector() {
    if (this.injector == null) {
      this.injector = Guice.createInjector(this.modules);
      this.modules.clear();
    } else if (!this.modules.isEmpty()) {
      this.injector = this.injector.createChildInjector(this.modules);
      this.modules.clear();
    }

    return this.injector;
  }

  public void setInjector(Injector injector) {
    this.injector = injector;
  }
}
