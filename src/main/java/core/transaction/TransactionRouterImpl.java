package core.transaction;

import core.model.CoinInfo;
import core.model.Exchange;
import core.model.Instrument;
import core.model.transaction.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is not a singleton, every time this is invoked it has to pull data from exchange
 */
public class TransactionRouterImpl implements TransactionRouter {
    private final ExchangeDataMap exchangeData;

    @Inject
    public TransactionRouterImpl(final ExchangeDataMap exchangeData) {
        this.exchangeData = exchangeData;
    }

    @Override
    public List<TransactionChainAndChainResult> getTradeChains(final Exchange baseExchange, final String baseCoin, final double baseCurrencyDeposit) {
        List<Trade> compatibleInstrumentPairs = getCompatibleInstrumentsBetweenAllExchanges();
        List<TransactionChain> allChains = new ArrayList<>();

        for (Trade trade : compatibleInstrumentPairs) {
            List<TransactionChain> chainsForTrade = generateDefaultChainForTrade(baseExchange, baseCoin, trade);
            allChains.addAll(chainsForTrade);
        }

        List<TransactionChainAndChainResult> ret = runChains(allChains, baseCurrencyDeposit);

        Collections.sort(ret, new TransactionChainAndChainResult.TransactionChainAndChainResultComparator());
        return ret;
    }

    private List<TransactionChain> generateDefaultChainForTrade(final Exchange baseExchange, final String baseCoin, final Trade trade) {
        List<TransactionChain> ret = new ArrayList<>();

        final boolean tradeStartCoinIsBaseCoin = trade.getFrom().getLeftSymbol().equals(baseCoin);
        final boolean tradeStartExchangeIsBaseExchange = trade.getFrom().getExchange().equals(baseExchange);
        List<Transaction> transactionsForTradeExecution = tradeAsTransactions(trade);
        if (!trade.getTo().getRightSymbol().equals(baseCoin)) {
            Transaction toBaseCoinTransaction = exchangeCoins(trade.getTo().getExchange(), trade.getTo().getRightSymbol(), baseCoin);
            if (toBaseCoinTransaction == null) {
                return ret;
            }
            transactionsForTradeExecution.add(toBaseCoinTransaction);
        }


        //if both need to convert and transfer send; create 2 chains convert->send and send->convert
        if (!tradeStartCoinIsBaseCoin && !tradeStartExchangeIsBaseExchange) {
            TransactionChain convertAndSendChain = new TransactionChain();
            Transaction exchangeTransaction1 = exchangeCoins(baseExchange, baseCoin, trade.getFrom().getLeftSymbol());
            Transaction transferTransaction1 = transferCoins(trade.getFrom().getLeftSymbol(), baseExchange, trade.getFrom().getExchange());
            if (exchangeTransaction1 != null) {
                convertAndSendChain.addToChain(exchangeTransaction1);
                convertAndSendChain.addToChain(transferTransaction1);
                convertAndSendChain.addToChain(transactionsForTradeExecution);
                ret.add(convertAndSendChain);
            }
            TransactionChain sendAndConvert = new TransactionChain();
            Transaction transferTransaction2 = transferCoins(baseCoin, baseExchange, trade.getFrom().getExchange());
            Transaction exchangeTransaction2 = exchangeCoins(trade.getFrom().getExchange(), baseCoin, trade.getFrom().getLeftSymbol());
            if (exchangeTransaction2 != null) {
                sendAndConvert.addToChain(transferTransaction2);
                sendAndConvert.addToChain(exchangeTransaction2);
                sendAndConvert.addToChain(transactionsForTradeExecution);
                ret.add(sendAndConvert);
            }
            return ret;
        }
        if (!tradeStartCoinIsBaseCoin) {
            Transaction exchangeTransaction = exchangeCoins(trade.getFrom().getExchange(), baseCoin, trade.getFrom().getLeftSymbol());
            if (exchangeTransaction != null) {
                TransactionChain convertAndExecute = new TransactionChain();
                convertAndExecute.addToChain(exchangeTransaction);
                convertAndExecute.addToChain(transactionsForTradeExecution);
                ret.add(convertAndExecute);
            }

            return ret;
        }
        if (!tradeStartExchangeIsBaseExchange) {
            Transaction transferTransaction = transferCoins(baseCoin, baseExchange, trade.getFrom().getExchange());
            TransactionChain transferAndExecute = new TransactionChain();
            transferAndExecute.addToChain(transferTransaction);
            transferAndExecute.addToChain(transactionsForTradeExecution);
            ret.add(transferAndExecute);
            return ret;
        }

        TransactionChain sameExchangeSameCurrencyChain = new TransactionChain(transactionsForTradeExecution);
        ret.add(sameExchangeSameCurrencyChain);
        return ret;
    }


    private List<Transaction> tradeAsTransactions(final Trade trade) {
        List<Transaction> ret = new ArrayList<>();
        Transaction legOneOfTrade = new ExchangeTransaction(trade.getFrom());

        ret.add(legOneOfTrade);
        if (trade.getFrom().getExchange() != trade.getTo().getExchange()) {
            Transaction transfer = transferCoins(trade.getFrom().getRightSymbol(), trade.getFrom().getExchange(), trade.getTo().getExchange());
            ret.add(transfer);
        }
        Transaction legTwoOfTrade = new ExchangeTransaction(trade.getTo());
        ret.add(legTwoOfTrade);
        return ret;
    }

    private ExchangeTransaction exchangeCoins(final Exchange exchange, final String iHaveCoin, final String iWantCoin) {
        for (Instrument pair : exchangeData.getInstruments(exchange)) {
            if (pair.getLeftSymbol().equals(iHaveCoin) && pair.getRightSymbol().equals(iWantCoin)) {
                return new ExchangeTransaction(pair);
            }
        }
        return null;
    }

    private TransferTransaction transferCoins(final String coin, final Exchange fromExchange, final Exchange toExchange) {
        final CoinInfo withdrawCoinInfo = exchangeData.getCoinInfo(coin, fromExchange);
        return new TransferTransaction(coin, withdrawCoinInfo.getWithdrawalFee(), fromExchange, toExchange);
    }


    private List<Trade> getCompatibleInstrumentsBetweenAllExchanges() {
        List<Trade> ret = new ArrayList<>();
        for (int i = 0; i < exchangeData.keySetSize() - 1; i++) {
            for (int j = i + 1; j < exchangeData.keySetSize(); j++) {
                ret.addAll(getCompatibleInstrumentsBetweenExchanges(
                        exchangeData.getInstruments(exchangeData.keyAtIndex(i)),
                        exchangeData.getInstruments(exchangeData.keyAtIndex(j))));
            }
        }
        return ret;
    }

    private List<Trade> getCompatibleInstrumentsBetweenExchanges(List<Instrument> fromExchange, List<Instrument> toExchange) {
        List<Trade> ret = new ArrayList<>();

        for (Instrument a : fromExchange) {
            for (Instrument b : toExchange) {
                final boolean outTransfer = a.getRightSymbol().equals(b.getLeftSymbol());
                final boolean inTransfer = b.getRightSymbol().equals(a.getLeftSymbol());
                if (outTransfer && inTransfer) {
                    ret.add(new Trade(a, b));
                    ret.add(new Trade(b, a));
                }
            }
        }
        return ret;
    }

    private List<TransactionChainAndChainResult> runChains(final List<TransactionChain> chains, final double baseCurrencyDeposit) {
        List<TransactionChainAndChainResult> ret = new ArrayList<>();
        for (TransactionChain chain : chains) {
            TransactionResult result = chain.getTransactionOutput(baseCurrencyDeposit);
            ret.add(new TransactionChainAndChainResult(chain, result));
        }
        return ret;
    }


    /**
     * Will return all possible arbitrage options inside the specified exchange
     * eg : eth-btc, btc-ada, ada-etc
     */
//    @Override
//    public List<TransactionChainAndChainResult> getSingleExchangeTrades(final Exchange exchange, final int maxChainSize, final String baseCurrency, final double baseCurrencyDeposit) {
//        List<TransactionChain> chains = new ArrayList<>();
//
//        List<List<Instrument>> possibleChains = combinationGenerator.getCombinations(maxChainSize, exchangeData.getInstruments(exchange).subList(0,200), baseCurrency);
//        for (List<Instrument> chainData : possibleChains) {
//            chains.add(toChain(chainData, exchange));
//        }
//
//        List<TransactionChainAndChainResult> chainsAndResult = runChains(chains, baseCurrencyDeposit);
//        Comparator<TransactionChainAndChainResult> transactionChainComparator = Comparator.comparing(o -> o.getChainRunResult().getCoinCount());
//        chainsAndResult.sort(transactionChainComparator);
//
//        return chainsAndResult;
//    }

}
