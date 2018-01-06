package api;

import core.model.Instrument;
import core.model.InstrumentInfo;

import java.util.List;
import java.util.Map;

public interface ExchangeManager {
    List<Instrument> getInstruments();
    Map<String, InstrumentInfo> getInstrumentInfo();
}
