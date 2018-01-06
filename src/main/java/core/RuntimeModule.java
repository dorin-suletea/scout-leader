package core;

import api.BittrexManager;
import api.BittrexManagerImpl;
import api.exchanges.*;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class RuntimeModule extends AbstractModule {
    private static final Injector injector = Guice.createInjector(new RuntimeModule());

    public static <T> T getInjectedObject(final Class<T> klass) {
        return injector.getInstance(klass);
    }

    @Override
    protected void configure() {
        bind(BittrexApi.class).to(BittrexApiImpl.class);
        bind(BittrexManager.class).to(BittrexManagerImpl.class);
    }

}
