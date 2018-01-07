package core.model.transaction;

import core.StringHelper;
import core.model.Exchange;
import core.model.Instrument;

public class ExchangeTransaction implements Transaction {
    private final Instrument instrument;
    private final Exchange exchange;

    public ExchangeTransaction(final Instrument instrument,
                               final Exchange exchange) {
        this.instrument = instrument;
        this.exchange = exchange;
    }

    @Override
    public TransactionResult getTransactionOutput(final double inputCoinCount) {
        final String resultCoin = instrument.getRightSymbol();
        final double resultCoinCount = instrument.getPrice() * inputCoinCount;

        return new TransactionResult(resultCoin, resultCoinCount);
    }

    @Override
    public String getSignature() {
        return exchange + " " + instrument.getInstrumentDirection() + " " + instrument.getRightSymbol() + " " + instrument.getLeftSymbol() + " ";
    }

    @Override
    public String toString() {
        return exchange + " " + instrument.getLeftSymbol() + " ---> " + instrument.getRightSymbol() + " ( " + StringHelper.formattedDouble(instrument.getPrice()) + " ) ";
    }

}
