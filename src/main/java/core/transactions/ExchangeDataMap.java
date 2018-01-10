package core.transactions;

import api.ExchangeManager;
import core.model.CoinInfo;
import core.model.Exchange;
import core.model.Instrument;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeDataMap {
    private final Map<Exchange, Pair<List<Instrument>, Map<String, CoinInfo>>> exchangeDataMap;

    public ExchangeDataMap(final List<ExchangeManager> exchangeManagers) {
        this.exchangeDataMap = new HashMap<>();

        for (ExchangeManager exchangeManager : exchangeManagers) {
            List<Instrument> instruments = exchangeManager.getInstruments();
            Map<String, CoinInfo> instrumentInfoMap = exchangeManager.getInstrumentInfo();
            Exchange exchange = exchangeManager.getExchange();
            exchangeDataMap.put(exchange, new Pair<>(instruments, instrumentInfoMap));
        }
    }

    public List<Instrument> getInstruments(final Exchange exchange) {
        return exchangeDataMap.get(exchange).getFirst();
    }

    public int keySetSize() {
        return exchangeDataMap.keySet().size();
    }

    public Exchange keyAtIndex(final int index) {
        return exchangeDataMap.keySet().toArray(new Exchange[]{})[index];
    }

    public CoinInfo getCoinInfo(final String coin, final Exchange exchange) {
        return exchangeDataMap.get(exchange).getSecond().get(coin);
    }


}
