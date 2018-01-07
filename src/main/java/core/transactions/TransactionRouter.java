package core.transactions;

import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;

import java.util.List;

public interface TransactionRouter {
    List<TransactionChainAndChainResult> getSingleExchangeTrades(final Exchange exchange, final int maxChainSize, final String baseCurrency, final double baseCurrencyDeposit);
}
