package api.exchanges;

import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;

import java.util.List;

public interface ExchangeApi {
    List<ApiInstrument> getInstruments();
    List<ApiInstrumentInfo> getInstrumentsInfo();
}
