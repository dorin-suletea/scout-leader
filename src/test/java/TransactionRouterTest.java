import api.BinanceManager;
import api.BittrexManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import core.model.Exchange;
import core.model.Instrument;
import core.model.InstrumentDirection;
import core.model.transaction.TransactionChainAndChainResult;
import core.transactions.TransactionRouter;
import core.transactions.TransactionRouterImpl;
import mocks.MockBinanceManagerImpl;
import mocks.MockBittrexManagerImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionRouterTest {
    private Injector injector;
    private final List<Instrument> testBittrexInstruments = new ArrayList<>();
    private final List<Instrument> testBinanceInstruments = new ArrayList<>();

    private final Exchange baseExchange = Exchange.BITTREX;
    private final String inputCoin = "ETH";
    private final int deposit = 1;


    private void withNewInjector() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(BittrexManager.class).toInstance(new MockBittrexManagerImpl(testBittrexInstruments));
                bind(BinanceManager.class).toInstance(new MockBinanceManagerImpl(testBinanceInstruments));
                bind(TransactionRouter.class).to(TransactionRouterImpl.class);
            }
        });
    }

    private void withInstruments(List<Instrument> bittrexInstruments, List<Instrument> binanceInstruments) {
        testBittrexInstruments.clear();
        testBittrexInstruments.addAll(bittrexInstruments);
        testBinanceInstruments.clear();
        testBinanceInstruments.addAll(binanceInstruments);
    }


    @Test
    public void testTradeStartsWithBaseCurrencyInBaseExchange() {
        withInstruments(
                Arrays.asList(new Instrument("ETH","BTC",0d, InstrumentDirection.BUY,Exchange.BITTREX)),
                Arrays.asList(new Instrument("BTC","ETH",0d, InstrumentDirection.SELL,Exchange.BINANCE))
        );
        withNewInjector();

        TransactionRouter router = injector.getInstance(TransactionRouter.class);
        List<TransactionChainAndChainResult> chainAndChainResult = router.getTradeChains(baseExchange, inputCoin, deposit);



        System.out.print(router);
    }
}
