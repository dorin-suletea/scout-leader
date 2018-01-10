package core.model.transaction;

import core.model.Instrument;

public class ExchangeTransaction implements Transaction {
    private final Instrument instrument;

    public ExchangeTransaction(final Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public TransactionResult getTransactionOutput(final double inputCoinCount) {
        final String resultCoin = instrument.getRightSymbol();
        final double resultCoinCount = instrument.getPrice() * inputCoinCount;

        return new TransactionResult(resultCoin, resultCoinCount);
    }

    @Override
    public String getSignature() {
        return "Trade : " + instrument.getLeftSymbol() + " --- " + instrument.getRightSymbol();
    }

    public String toDebugString(final double inputCoinCount) {
        final TransactionResult transactionResult = getTransactionOutput(inputCoinCount);
        return "Exchange : " + instrument.getLeftSymbol() + " --- " + instrument.getRightSymbol() + " " + transactionResult.toString() + " " + instrument.getExchange() + " DEBIG "+instrument;
    }

}
