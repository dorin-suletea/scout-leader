package core.transaction.strategy;

import api.CoinBlacklist;
import core.model.CoinInfo;
import core.model.transaction.ExchangeTransaction;
import core.model.transaction.Transaction;
import core.model.transaction.TransactionChain;
import core.model.transaction.TransferTransaction;
import core.transaction.ExchangeDataMap;
import core.transaction.FastTxCoinProvider;
import core.transaction.TransactionHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class TransferStrategyFactoryImpl implements TransferStrategyFactory {
    private final FastTxCoinProvider fastTxCoinProvider;

    @Inject
    public TransferStrategyFactoryImpl(final FastTxCoinProvider fastTxCoinProvider) {
        this.fastTxCoinProvider = fastTxCoinProvider;
    }


    private TransferStrategy makeSimpleTransferStrategy(final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData) {
        return (coin, fromExchange, toExchange) -> {
            if (coinBlacklist.isCoinBlackListed(fromExchange, coin) || coinBlacklist.isCoinBlackListed(toExchange, coin)) {
                //coin is blacklisted on one of the exchanges, info incomplete = > invalidate chain
                return Collections.singletonList(TransactionChain.VOID_CHAIN);
            }
            final CoinInfo withdrawCoinInfo = exchangeData.getCoinInfo(coin, fromExchange);
            Transaction simpleTransferTransaction = new TransferTransaction(coin, withdrawCoinInfo.getWithdrawalFee(), fromExchange, toExchange);
            return Collections.singletonList(new TransactionChain(simpleTransferTransaction));
        };
    }


    private TransferStrategy makeFastTransferStrategy(final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData) {
        return (coin, fromExchange, toExchange) -> {
            List<TransactionChain> ret = new ArrayList<>();
            for (String fastCoin : fastTxCoinProvider.getFastTransactionCoins()) {
                if (coinBlacklist.isCoinBlackListed(fromExchange, fastCoin) || coinBlacklist.isCoinBlackListed(toExchange, fastCoin)) {
                    continue;
                }

                if (fastCoin.equals(coin)) {
                    //becomes simple strategy since the transfer is already fastCoin
                    final CoinInfo withdrawCoinInfo = exchangeData.getCoinInfo(coin, fromExchange);
                    Transaction simpleTransferTransaction = new TransferTransaction(coin, withdrawCoinInfo.getWithdrawalFee(), fromExchange, toExchange);
                    ret.add(new TransactionChain(simpleTransferTransaction));
                    continue;
                }

                //normal case (baseCoin is not a fastCoin)
                ExchangeTransaction exchangeIntoFastCoin = TransactionHelper.exchangeCoins(exchangeData, fromExchange, coin, fastCoin);
                //todo,validate this in a better way
                if (exchangeIntoFastCoin==null){
                    continue;
                }
                TransferTransaction transferTransaction = new TransferTransaction(exchangeIntoFastCoin.getResultCoin(), exchangeData.getCoinInfo(fastCoin, fromExchange).getWithdrawalFee(), fromExchange, toExchange);
                ExchangeTransaction exchangeFromFastCoin = TransactionHelper.exchangeCoins(exchangeData, fromExchange, transferTransaction.getResultCoin(), coin);
                //todo,validate this in a better way
                if (exchangeFromFastCoin==null){
                    continue;
                }




                TransactionChain newChain = new TransactionChain(exchangeIntoFastCoin, transferTransaction,exchangeFromFastCoin);
                if (newChain.isValidChain()) {
                    ret.add(newChain);
                }
            }

            return ret;
        };
    }

    @Override
    public TransferStrategy makeTransferStrategy(final TransferStrategyType desiredTransferType, final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData) {
        switch (desiredTransferType) {
            case SIMPLE:
                return makeSimpleTransferStrategy(coinBlacklist, exchangeData);
            case FASTCOIN:
                return makeFastTransferStrategy(coinBlacklist, exchangeData);

        }
        throw new RuntimeException("Unknown transfer strategy type " + desiredTransferType);
    }
}
