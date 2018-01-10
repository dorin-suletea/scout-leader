package mocks;

import core.model.Instrument;

import java.util.List;

public class MockManager {
    private final List<Instrument> instruments;

    public MockManager(final List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }


    //TODO, fix this so you can pass the exchange
    public Map<String, CoinInfo> getCoinInfo() {
        Map<String, CoinInfo> ret = new HashMap<>();
        for (Instrument i : instruments) {
            ret.put(i.getLeftSymbol(), new CoinInfo(i.getLeftSymbol(), 0, true, Exchange.BINANCE));
            ret.put(i.getRightSymbol(), new CoinInfo(i.getRightSymbol(), 0, true, Exchange.BINANCE));
        }
        return ret;
    }
}
