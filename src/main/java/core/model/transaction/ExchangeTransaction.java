package core.model.transaction;

import core.StringHelper;
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
        return "Exchange " + instrument.getExchange() + " " + instrument.getInstrumentDirection() + " " + instrument.getRightSymbol() + " " + instrument.getLeftSymbol() + " ";
    }

    @Override
    public String toString() {
        return instrument.getExchange() + " " + instrument.getRightSymbol() + " == " + instrument.getLeftSymbol() + " ( " + StringHelper.formattedDouble(instrument.getPrice()) + " ) ";
    }

}
