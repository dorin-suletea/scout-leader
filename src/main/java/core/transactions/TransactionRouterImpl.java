package core.transactions;

import api.BittrexManager;
import core.model.Exchange;
import core.model.Instrument;
import core.model.transaction.ExchangeTransaction;
import core.model.transaction.TransactionChain;
import core.model.transaction.TransactionChainAndChainResult;
import core.model.transaction.TransactionResult;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This is not a singleton, every time this is invoked it has to pull data from exchange
 */
public class TransactionRouterImpl implements TransactionRouter {
    private final ExchangeDataMap exchangeData;
    private final CombinationGenerator combinationGenerator;

    @Inject
    public TransactionRouterImpl(final BittrexManager bittrexManager, final CombinationGenerator combinationGenerator) {
        exchangeData = new ExchangeDataMap(bittrexManager);
        this.combinationGenerator = combinationGenerator;
    }

    /**
     * Will return all possible arbitrage options inside the specified exchange
     * eg : eth-btc, btc-ada, ada-etc
     */
    @Override
    public List<TransactionChainAndChainResult> getSingleExchangeTrades(final Exchange exchange, final int maxChainSize, final String baseCurrency, final double baseCurrencyDeposit) {
        List<TransactionChain> chains = new ArrayList<>();

        List<List<Instrument>> possibleChains = combinationGenerator.getCombinations(maxChainSize, exchangeData.getInstruments(exchange).subList(0,200), baseCurrency);
        for (List<Instrument> chainData : possibleChains) {
            chains.add(toChain(chainData, exchange));
        }

        List<TransactionChainAndChainResult> chainsAndResult = runChains(chains, baseCurrencyDeposit);
        Comparator<TransactionChainAndChainResult> transactionChainComparator = Comparator.comparing(o -> o.getChainRunResult().getCoinCount());
        chainsAndResult.sort(transactionChainComparator);

        return chainsAndResult;
    }


    private List<TransactionChainAndChainResult> runChains(final List<TransactionChain> chains, final double baseCurrencyDeposit) {
        List<TransactionChainAndChainResult> ret = new ArrayList<>();
        for (TransactionChain chain : chains) {
            TransactionResult result = chain.getTransactionOutput(baseCurrencyDeposit);
            ret.add(new TransactionChainAndChainResult(chain, result));
        }
        return ret;
    }

    private TransactionChain toChain(final List<Instrument> inputChain, final Exchange exchange) {
        TransactionChain newChain = new TransactionChain();
        for (Instrument instrument : inputChain) {
            newChain.addToChain(new ExchangeTransaction(instrument, exchange));
        }
        return newChain;
    }

}
