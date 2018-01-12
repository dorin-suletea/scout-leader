package core;

import api.BinanceManager;
import api.BinanceManagerImpl;
import api.BittrexManager;
import api.BittrexManagerImpl;
import api.exchanges.BinanceApi;
import api.exchanges.BinanceApiImpl;
import api.exchanges.BittrexApi;
import api.exchanges.BittrexApiImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import core.transaction.ExchangeDataMap;
import core.transaction.TransactionRouter;
import core.transaction.TransactionRouterImpl;

public class RuntimeModule extends AbstractModule {
    private static final Injector injector = Guice.createInjector(new RuntimeModule());

    public static <T> T getInjectedObject(final Class<T> klass) {
        return injector.getInstance(klass);
    }

    @Override
    protected void configure() {
        bind(BittrexApi.class).to(BittrexApiImpl.class);
        bind(BittrexManager.class).to(BittrexManagerImpl.class);
        bind(BinanceApi.class).to(BinanceApiImpl.class);
        bind(BinanceManager.class).to(BinanceManagerImpl.class);
        bind(ExchangeDataMap.class);
        bind(TransactionRouter.class).to(TransactionRouterImpl.class);

    }

}
