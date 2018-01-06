package api.exchanges;

import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public interface BittrexApi {
    String BITTREX_URL = "https://bittrex.com/api/v1.1";
    String INSTRUMENTS_URL = BITTREX_URL + "/public/getmarketsummaries";
    String WITHDRAWAL_FEES_URL = BITTREX_URL + "/public/getcurrencies";

    List<ApiInstrument> getInstruments();
    List<ApiInstrumentInfo> getInstrumentsInfo();
}
