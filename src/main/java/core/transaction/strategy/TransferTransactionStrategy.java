package core.transaction.strategy;

import core.model.Exchange;
import core.model.transaction.Transaction;

import java.util.List;

public interface TransferTransactionStrategy {
    List<Transaction> transferCoinAlternatives(final String coin, final Exchange fromExchange, final Exchange toExchange);
}
