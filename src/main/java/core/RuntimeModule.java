package core;

import api.*;
import api.exchanges.*;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import core.transaction.*;
import core.transaction.strategy.TransferStrategyFactory;
import core.transaction.strategy.TransferStrategyFactoryImpl;

public class RuntimeModule extends AbstractModule {
    private static final Injector injector = Guice.createInjector(new RuntimeModule());

    public static <T> T getInjectedObject(final Class<T> klass) {
        return injector.getInstance(klass);
    }

    @Override
    protected void configure() {
        bind(BittrexApi.class).to(BittrexApiImpl.class);
        bind(BinanceApi.class).to(BinanceApiImpl.class);
        bind(PoloniexApi.class).to(PoloniexApiImpl.class);

        bind(BittrexManager.class).to(BittrexManagerImpl.class);
        bind(BinanceManager.class).to(BinanceManagerImpl.class);
        bind(PoloniexManager.class).to(PoloniexManagerImpl.class);

        bind(ExchangeDataMap.class);
        bind(CoinBlacklist.class).to(CoinBlacklistImpl.class);
        bind(FastTxCoinProvider.class).to(FastTxCoinProviderImpl.class);
        bind(TransferStrategyFactory.class).to(TransferStrategyFactoryImpl.class);
        bind(TransactionRouter.class).to(TransactionRouterImpl.class);
    }

}
