package mocks;

import api.exchanges.BinanceManager;
import core.model.Exchange;
import core.model.Instrument;

import java.util.List;

public class MockBinanceManagerImpl extends MockManager implements BinanceManager {
    public MockBinanceManagerImpl(List<Instrument> instruments) {
        super(instruments);
    }

    @Override
    public List<Instrument> getInstruments() {
        return super.getInstruments();
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BINANCE;
    }
}
