package core.transaction;

import api.CoinBlacklist;
import core.model.Exchange;
import core.model.Instrument;
import core.model.transaction.Transaction;
import core.model.transaction.TransactionChain;
import core.model.transaction.TransactionChainAndChainResult;
import core.model.transaction.TransactionResult;
import core.transaction.strategy.TransferStrategy;
import core.transaction.strategy.TransferStrategyFactory;
import core.transaction.strategy.TransferStrategyType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is not a singleton, every time this is invoked it has to pull data from exchange
 */
public class TransactionRouterImpl implements TransactionRouter {
    private final ExchangeDataMap exchangeData;
    private final CoinBlacklist coinBlacklist;
    private final TransferStrategyFactory transferStrategyFactory;

    @Inject
    public TransactionRouterImpl(final ExchangeDataMap exchangeData, final CoinBlacklist coinBlacklist, final TransferStrategyFactory transferStrategyFactory) {
        this.exchangeData = exchangeData;
        this.coinBlacklist = coinBlacklist;
        this.transferStrategyFactory = transferStrategyFactory;
    }

    @Override
    public List<TransactionChainAndChainResult> getTradeChains(final Exchange baseExchange, final String baseCoin, final double baseCurrencyDeposit, final TransferStrategyType transferStrategyType) {
        List<Trade> compatibleInstrumentPairs = getCompatibleInstrumentsBetweenAllExchanges();
        List<TransactionChain> allChains = new ArrayList<>();

        TransferStrategy transferStrategy = transferStrategyFactory.makeTransferStrategy(transferStrategyType, coinBlacklist, exchangeData);
        for (Trade trade : compatibleInstrumentPairs) {
            List<TransactionChain> chainsForTrade = generateChainsForTrade(baseExchange, baseCoin, trade, transferStrategy);
            allChains.addAll(chainsForTrade);
        }

        List<TransactionChainAndChainResult> ret = runChains(allChains, baseCurrencyDeposit);

        Collections.sort(ret, new TransactionChainAndChainResult.TransactionChainAndChainResultComparator());
        return ret;
    }


    private List<TransactionChain> generateChainsForTrade(final Exchange baseExchange, final String baseCoin, final Trade trade, final TransferStrategy transferStrategy) {
        final boolean tradeStartCoinIsBaseCoin = trade.getFrom().getLeftSymbol().equals(baseCoin);
        final boolean tradeStartExchangeIsBaseExchange = trade.getFrom().getExchange().equals(baseExchange);
        List<TransactionChain> executeTradeSubChains = tradeAsSubChainOptions(trade, baseCoin, transferStrategy);


        if (!tradeStartCoinIsBaseCoin && !tradeStartExchangeIsBaseExchange) {
            List<TransactionChain> chainOptionsForDifferentExchangeAndCoin = new ArrayList<>();
            List<TransactionChain> setupTradeSubChains = tradeSetupOptionsForNonBaseExchangeAndNonBaseCurrency(baseExchange, baseCoin, trade, transferStrategy);
            for (TransactionChain setup : setupTradeSubChains) {
                for (TransactionChain tradeChain : executeTradeSubChains) {
                    chainOptionsForDifferentExchangeAndCoin.add(new TransactionChain(setup, tradeChain));
                }
            }
            return filterOutInvalidChains(chainOptionsForDifferentExchangeAndCoin);
        }


        if (!tradeStartCoinIsBaseCoin) {
            List<TransactionChain> convertAndExecute = new ArrayList<>();
            Transaction exchangeTransaction = TransactionHelper.exchangeCoins(exchangeData, trade.getFrom().getExchange(), baseCoin, trade.getFrom().getLeftSymbol());
            for (TransactionChain tradeChain : executeTradeSubChains) {
                TransactionChain chainOption = new TransactionChain();
                chainOption.addToChain(exchangeTransaction);
                chainOption.addToChain(tradeChain);
                convertAndExecute.add(chainOption);
            }
            return filterOutInvalidChains(convertAndExecute);
        }


        if (!tradeStartExchangeIsBaseExchange) {
            List<TransactionChain> transferAndExecute = new ArrayList<>();
            List<TransactionChain> transferOptions = transferStrategy.transferCoinAlternatives(baseCoin, baseExchange, trade.getFrom().getExchange());

            for (TransactionChain setup : transferOptions) {
                for (TransactionChain tradeChain : executeTradeSubChains) {
                    TransactionChain transferAndExecuteChainOption = new TransactionChain();
                    transferAndExecuteChainOption.addToChain(setup);
                    transferAndExecuteChainOption.addToChain(tradeChain);
                    transferAndExecute.add(transferAndExecuteChainOption);
                }
            }
            return filterOutInvalidChains(transferAndExecute);
        }

        //trade starts at same exchange and currency
        return filterOutInvalidChains(executeTradeSubChains);
    }

    /**
     * if both need to convert and transfer send; create 2 chains convert->send and send->convert
     **/
    private List<TransactionChain> tradeSetupOptionsForNonBaseExchangeAndNonBaseCurrency(final Exchange baseExchange, final String baseCoin, final Trade trade, final TransferStrategy transferStrategy) {
        List<TransactionChain> convertAndSendChains = new ArrayList<>();

        //convert and send
        List<TransactionChain> transferOptions1 = transferStrategy.transferCoinAlternatives(trade.getFrom().getLeftSymbol(), baseExchange, trade.getFrom().getExchange());
        Transaction exchangeTransaction1 = TransactionHelper.exchangeCoins(exchangeData, baseExchange, baseCoin, trade.getFrom().getLeftSymbol());
        for (TransactionChain transferOption : transferOptions1) {
            convertAndSendChains.add(new TransactionChain(exchangeTransaction1, transferOption));
        }

        //send and convert
        List<TransactionChain> sendAndConvertChains = new ArrayList<>();
        List<TransactionChain> transferOptions2 = transferStrategy.transferCoinAlternatives(baseCoin, baseExchange, trade.getFrom().getExchange());
        for (TransactionChain transferOption : transferOptions2) {
            Transaction exchangeTransaction2 = TransactionHelper.exchangeCoins(exchangeData, transferOption.getResultExchange(), transferOption.getResultCoin(), trade.getFrom().getLeftSymbol());
            sendAndConvertChains.add(new TransactionChain(transferOption, exchangeTransaction2));
        }

        List<TransactionChain> allChains = new ArrayList<>();
        allChains.addAll(convertAndSendChains);
        allChains.addAll(sendAndConvertChains);

        return filterOutInvalidChains(allChains);
    }


    private List<TransactionChain> tradeAsSubChainOptions(final Trade trade, final String baseCoin, final TransferStrategy transferStrategy) {
        List<TransactionChain> chains = new ArrayList<>();

        boolean isSameExchangeTrade = trade.getFrom().getExchange() == trade.getTo().getExchange();

        Transaction legOneOfTrade = TransactionHelper.exchangeCoins(exchangeData, trade.getFrom());
        Transaction toBaseCoinTransaction = TransactionHelper.exchangeCoins(exchangeData, trade.getTo().getExchange(), trade.getTo().getRightSymbol(), baseCoin);

        //no need to transfer
        if (isSameExchangeTrade) {

            chains.add(new TransactionChain(Arrays.asList(
                    legOneOfTrade,
                    TransactionHelper.exchangeCoins(exchangeData, trade.getTo()),
                    toBaseCoinTransaction
            )));
        } else {
            List<TransactionChain> possibleTransferTransactions = transferStrategy.transferCoinAlternatives(legOneOfTrade.getResultCoin(), trade.getFrom().getExchange(), trade.getTo().getExchange());
            for (TransactionChain transferOption : possibleTransferTransactions) {
                if (transferOption.isValid()) {
                    chains.add(new TransactionChain(Arrays.asList(
                            legOneOfTrade,
                            transferOption,
                            TransactionHelper.exchangeCoins(exchangeData, trade.getTo().getExchange(), transferOption.getResultCoin(), trade.getTo().getRightSymbol()),
                            toBaseCoinTransaction
                    )));
                }
            }
        }

        return filterOutInvalidChains(chains);
    }


    private List<TransactionChain> filterOutInvalidChains(List<TransactionChain> unfilteredChains) {
        List<TransactionChain> ret = new ArrayList<>();
        for (TransactionChain c : unfilteredChains) {
            if (c.isValid()) {
                ret.add(c);
            }
        }
        return ret;
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
