package core.transaction.strategy;

import api.CoinBlacklist;
import core.transaction.ExchangeDataMap;

public interface TransferTransactionFactory {
    TransferTransactionStrategy makeSimpleTransferStrategy(final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData);
    TransferTransactionStrategy makeFastTransferStrategy(final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData);
}
