package core.transaction;

import api.BinanceManager;
import api.BittrexManager;
import api.ExchangeManager;
import api.PoloniexManager;
import core.model.CoinInfo;
import core.model.Exchange;
import core.model.Instrument;
import org.apache.commons.math3.util.Pair;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeDataMap {
    private final Map<Exchange, Pair<List<Instrument>, Map<String, CoinInfo>>> exchangeDataMap;

    @Inject
    public ExchangeDataMap(final BittrexManager bittrexManager, final BinanceManager binanceManager, final PoloniexManager poloniexManager) {
        this.exchangeDataMap = new HashMap<>();
        final List<ExchangeManager> exchangeManagers = Arrays.asList(bittrexManager, binanceManager, poloniexManager);

        for (ExchangeManager exchangeManager : exchangeManagers) {
            List<Instrument> instruments = exchangeManager.getInstruments();
            Map<String, CoinInfo> instrumentInfoMap = exchangeManager.getCoinInfo();
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
        final Map<String, CoinInfo> coinInfoMap = exchangeDataMap.get(exchange).getSecond();
        if (!coinInfoMap.containsKey(coin)) {
            throw new RuntimeException("Unknown withdrawal fee for " + coin + " Exchange: " + exchange);
        }
        return coinInfoMap.get(coin);
    }


}
