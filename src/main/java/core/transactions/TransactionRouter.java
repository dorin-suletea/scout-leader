package core.transactions;

import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;

import java.util.List;

public interface TransactionRouter {
    List<TransactionChainAndChainResult> getTradeChains(final Exchange baseExchange, final String baseCoin, final double baseCurrencyDeposit);
}
