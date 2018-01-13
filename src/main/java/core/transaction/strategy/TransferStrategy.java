package core.transaction.strategy;

import core.model.Exchange;
import core.model.transaction.TransactionChain;

import java.util.List;

public interface TransferStrategy {
    List<TransactionChain> transferCoinAlternatives(final String coin, final Exchange fromExchange, final Exchange toExchange);
}
