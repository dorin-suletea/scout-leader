package mocks;

import api.BittrexManager;
import core.model.CoinInfo;
import core.model.Exchange;
import core.model.Instrument;

import java.util.List;
import java.util.Map;

public class MockBittrexManagerImpl extends MockManager implements BittrexManager {

    public MockBittrexManagerImpl(List<Instrument> instruments) {
        super(instruments);
    }

    @Override
    public List<Instrument> getInstruments() {
        return super.getInstruments();
    }

    @Override
    public Map<String, CoinInfo> getCoinInfo() {
        return null;
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BITTREX;
    }
}
