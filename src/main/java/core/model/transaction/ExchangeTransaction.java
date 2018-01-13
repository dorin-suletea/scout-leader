package core.model.transaction;

import core.Constants;
import core.StringHelper;
import core.model.Exchange;
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
        return this.getClass().getSimpleName() + " : " + instrument.getLeftSymbol() + " --- " + instrument.getRightSymbol() + " " + Constants.TRANSACTION_SIGNATURE_DELIMITER + " ";
    }

    @Override
    public String getResultCoin() {
        return instrument.getRightSymbol();
    }

    @Override
    public Exchange getResultExchange() {
        return instrument.getExchange();
    }

    @Override
    public String toString() {
        return "Exchange : " + instrument.getLeftSymbol() + " --- " + instrument.getRightSymbol() + " " + instrument.getExchange()
                + " [Price " + StringHelper.formattedDouble(instrument.getPrice()) + "]"
                + " [RevPrice " + StringHelper.formattedDouble(1 / instrument.getPrice()) + "]";
    }

}
