package mocks;

import api.BittrexManager;
import core.model.Exchange;
import core.model.Instrument;

import java.util.List;

public class MockBittrexManagerImpl extends MockManager implements BittrexManager {

    public MockBittrexManagerImpl(List<Instrument> instruments) {
        super(instruments);
    }

    @Override
    public List<Instrument> getInstruments() {
        return super.getInstruments();
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BITTREX;
    }
}
