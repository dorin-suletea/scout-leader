package core.transaction.strategy;

import api.CoinBlacklist;
import core.transaction.ExchangeDataMap;

public interface TransferStrategyFactory {
    TransferStrategy makeTransferStrategy(final TransferStrategyType desiredTransferType, final CoinBlacklist coinBlacklist, final ExchangeDataMap exchangeData);
}
