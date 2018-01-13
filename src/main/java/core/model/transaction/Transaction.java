package core.model.transaction;

import core.model.Exchange;

public interface Transaction {
    TransactionResult getTransactionOutput(final double baseCurrencyDeposit);

    /**
     * checks if the transaction is the same symbol and direction wise.
     * does not account for pair prices
     */
    String getSignature();

    String getResultCoin();

    Exchange getResultExchange();
}
