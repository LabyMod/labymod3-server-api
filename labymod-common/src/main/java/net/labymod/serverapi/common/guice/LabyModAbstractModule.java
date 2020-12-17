package net.labymod.serverapi.common.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public abstract class LabyModAbstractModule extends AbstractModule {

  public <T> void bind(TypeLiteral<T> source, Class<? extends T> target) {
    this.bind(source).to(target);
  }

  public <T> void bind(Class<T> source, Class<? extends T> target) {
    this.bind(source).to(target);
  }

  protected  <T> void installFactory(Class<T> source, Class<? extends T> target, Class<?> factory) {
    this.install(new FactoryModuleBuilder().implement(source, target).build(factory));
  }
}
