package core.model.transaction;

import core.Constants;
import core.model.Exchange;

public class TransferTransaction implements Transaction {
    private final String coinSymbol;
    private final Exchange fromExchange;
    private final Exchange toExchange;
    private final double withdrawalFee;

    public TransferTransaction(final String coinSymbol,
                               final double withdrawalFee,
                               final Exchange fromExchange,
                               final Exchange toExchange) {
        this.coinSymbol = coinSymbol;
        this.fromExchange = fromExchange;
        this.toExchange = toExchange;
        this.withdrawalFee = withdrawalFee;
    }

    @Override
    public TransactionResult getTransactionOutput(final double inputCoinCount) {
        return new TransactionResult(coinSymbol, inputCoinCount - withdrawalFee);
    }

    @Override
    public String getSignature() {
        return this.getClass().getSimpleName() + " : " + coinSymbol + " " + fromExchange + " -- " + toExchange + " " + Constants.TRANSACTION_SIGNATURE_DELIMITER + " ";
    }

    @Override
    public String toString() {
        return "Transfer : " + coinSymbol + " " + fromExchange + " -- " + toExchange + " ";
    }
}
