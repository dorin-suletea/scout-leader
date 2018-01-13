package core.transaction;

import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;
import core.transaction.strategy.TransferStrategyType;

import java.util.List;

public interface TransactionRouter {
    List<TransactionChainAndChainResult> getTradeChains(final Exchange baseExchange,
                                                        final String baseCoin,
                                                        final double baseCurrencyDeposit,
                                                        final TransferStrategyType transferStrategyType);
}
