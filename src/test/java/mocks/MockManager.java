package mocks;

import api.exchanges.ExchangeManager;
import core.model.CoinInfo;
import core.model.Instrument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MockManager implements ExchangeManager {
    private final List<Instrument> instruments;

    public MockManager(final List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }


    public Map<String, CoinInfo> getCoinInfo() {
        Map<String, CoinInfo> ret = new HashMap<>();
        for (Instrument i : instruments) {
            ret.put(i.getLeftSymbol(), new CoinInfo(i.getLeftSymbol(), 0, true, this.getExchange()));
            ret.put(i.getRightSymbol(), new CoinInfo(i.getRightSymbol(), 0, true, this.getExchange()));
        }
        return ret;
    }
}
