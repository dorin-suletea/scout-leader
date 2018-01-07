package core;

import api.BittrexManager;
import api.BittrexManagerImpl;
import api.exchanges.BittrexApi;
import api.exchanges.BittrexApiImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import core.transactions.CombinationGenerator;
import core.transactions.CombinationGeneratorImpl;
import core.transactions.TransactionRouter;
import core.transactions.TransactionRouterImpl;

public class RuntimeModule extends AbstractModule {
    private static final Injector injector = Guice.createInjector(new RuntimeModule());

    public static <T> T getInjectedObject(final Class<T> klass) {
        return injector.getInstance(klass);
    }

    @Override
    protected void configure() {
        bind(BittrexApi.class).to(BittrexApiImpl.class);
        bind(BittrexManager.class).to(BittrexManagerImpl.class);
        bind(CombinationGenerator.class).to(CombinationGeneratorImpl.class);
        bind(TransactionRouter.class).to(TransactionRouterImpl.class);

    }

}
