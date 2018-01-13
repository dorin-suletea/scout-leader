import api.BinanceManager;
import api.BittrexManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import core.RuntimeModule;
import core.model.Exchange;
import core.model.Instrument;
import core.model.transaction.Transaction;
import core.model.transaction.TransactionChain;
import core.model.transaction.TransactionChainAndChainResult;
import core.model.transaction.TransferTransaction;
import core.transaction.TransactionRouter;
import core.transaction.TransactionRouterImpl;
import core.transaction.strategy.TransferStrategyType;
import mocks.MockBinanceManagerImpl;
import mocks.MockBittrexManagerImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TransactionRouterTest {
    private Injector injector;
    private final List<Instrument> testBittrexInstruments = new ArrayList<>();
    private final List<Instrument> testBinanceInstruments = new ArrayList<>();

    private final Exchange baseExchange = Exchange.BITTREX;
    private final String inputCoin = "ETH";
    private final Double deposit = 1d;


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
//        withInstruments(
//                Arrays.asList(
//                        new Instrument("ETH", "MTL", 1d, InstrumentDirection.BUY, Exchange.BITTREX),
//                        new Instrument("MTL", "ETH", 1d, InstrumentDirection.BUY, Exchange.BITTREX)
//                ),
//                Arrays.asList(
//                        new Instrument("MTL", "ETH", 1d, InstrumentDirection.SELL, Exchange.BINANCE),
//                        new Instrument("ETH", "MTL", 1d, InstrumentDirection.SELL, Exchange.BINANCE)
//                )
//        );
//        withNewInjector();
//
//        TransactionRouter router = injector.getInstance(TransactionRouter.class);
//        List<TransactionChainAndChainResult> chainAndChainResult = router.getTradeChains(baseExchange, inputCoin, deposit, TransferStrategyType.SIMPLE);
//
//
//        for (TransactionChainAndChainResult chain : chainAndChainResult) {
//            assertChainDoesNotStartWithTransfer(chain.getChain());
//        }
    }

    @Test
    public void testChainsLinkUsingSimpleTransferStrategy() {
        TransactionRouter router = RuntimeModule.getInjectedObject((TransactionRouter.class));
        List<TransactionChainAndChainResult> chainAndChainResult = router.getTradeChains(baseExchange, inputCoin, deposit, TransferStrategyType.SIMPLE);
        assertTransactionsLink(chainAndChainResult);
    }

    @Test
    public void testChainsLinkUsingFastCoinTransferStrategy() {
        TransactionRouter router = RuntimeModule.getInjectedObject((TransactionRouter.class));
        List<TransactionChainAndChainResult> chainAndChainResult = router.getTradeChains(baseExchange, inputCoin, deposit, TransferStrategyType.FASTCOIN);
        assertTransactionsLink(chainAndChainResult);
    }



    public void assertTransactionsLink(List<TransactionChainAndChainResult> chainResults) {
        for (TransactionChainAndChainResult chain : chainResults) {
            String tempInputCoin = inputCoin;
            for (Transaction transaction : chain.getChain().flatten()) {
                Assert.assertEquals(chain.getChain().toString(), tempInputCoin, transaction.getInputCoin());
                tempInputCoin = transaction.getResultCoin();
            }
            Assert.assertEquals(tempInputCoin, inputCoin);
        }

    }


    private void assertChainDoesNotStartWithTransfer(final TransactionChain chain) {
        List<String> transactionSignatures = TestHelper.splitChainIntoTransactionSignatures(chain);
        Assert.assertFalse("Chain cant start with Transfer " + chain.getSignature(), transactionSignatures.get(0).contains(TransferTransaction.class.getSimpleName()));
    }
}
