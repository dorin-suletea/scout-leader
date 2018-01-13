package core.transaction;

import core.model.Exchange;
import core.model.Instrument;
import core.model.transaction.ExchangeTransaction;

public class TransactionHelper {


    public static ExchangeTransaction exchangeCoins(final ExchangeDataMap exchangeDataMap, final Exchange exchange, final String iHaveCoin, final String iWantCoin) {
        for (Instrument pair : exchangeDataMap.getInstruments(exchange)) {
            if (pair.getLeftSymbol().equals(iHaveCoin) && pair.getRightSymbol().equals(iWantCoin)) {
                return new ExchangeTransaction(pair);
            }
        }
        return null;
    }

    public static ExchangeTransaction exchangeCoins(final ExchangeDataMap exchangeDataMap, final Instrument instrument) {
        for (Instrument pair : exchangeDataMap.getInstruments(instrument.getExchange())) {
            if (pair.getLeftSymbol().equals(instrument.getLeftSymbol()) && pair.getRightSymbol().equals(instrument.getRightSymbol())) {
                return new ExchangeTransaction(pair);
            }
        }
        return null;
    }

}
