package api.exchanges.api;

import api.ApiInstrument;
import api.ApiInstrumentInfo;

import java.util.List;

public interface ExchangeApi {
    List<ApiInstrument> getInstruments();
    List<ApiInstrumentInfo> getInstrumentsInfo();
}
