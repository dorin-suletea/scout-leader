package core.transactions;

import api.ExchangeManager;
import core.model.Exchange;
import core.model.Instrument;
import core.model.InstrumentInfo;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeDataMap {
    private final Map<Exchange,Pair<List<Instrument>,Map<String,InstrumentInfo>>> exchangeDataMap;

    public ExchangeDataMap(final ExchangeManager exchangeManager) {
        List<Instrument> instruments = exchangeManager.getInstruments();
        Map<String,InstrumentInfo> instrumentInfoMap = exchangeManager.getInstrumentInfo();
        Exchange exchange = exchangeManager.getExchange();
        exchangeDataMap = new HashMap<>();
        exchangeDataMap.put(exchange,new Pair<>(instruments,instrumentInfoMap));
    }

    public List<Instrument> getInstruments(final Exchange exchange){
        return exchangeDataMap.get(exchange).getFirst();
    }


}
