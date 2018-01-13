package core.transaction.strategy;

import api.CoinBlacklist;
import core.model.CoinInfo;
import core.model.transaction.TransferTransaction;
import core.transaction.ExchangeDataMap;

import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class TransferTransactionFactoryImpl implements TransferTransactionFactory {

    @Override
    public TransferTransactionStrategy makeSimpleTransferStrategy(final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData) {
        return (coin, fromExchange, toExchange) -> {
            if (coinBlacklist.isCoinBlackListed(fromExchange, coin) || coinBlacklist.isCoinBlackListed(toExchange, coin)) {
                //coin is blacklisted on one of the exchanges, info incomplete = > invalidate chain
                return Collections.emptyList();
            }
            final CoinInfo withdrawCoinInfo = exchangeData.getCoinInfo(coin, fromExchange);
            return Collections.singletonList(new TransferTransaction(coin, withdrawCoinInfo.getWithdrawalFee(), fromExchange, toExchange));
        };
    }

    @Override
    public TransferTransactionStrategy makeFastTransferStrategy(final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData) {
        return null;
    }
}
