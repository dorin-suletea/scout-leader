package api.exchanges.api;

import api.model.ApiAsset;
import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;

import java.util.List;

public interface ExchangeApi {
    List<ApiInstrument> getInstruments();
    List<ApiInstrumentInfo> getInstrumentsInfo();
    List<ApiAsset> getAssets();
    String getDepositAddress(final String coin);
}
