package api.exchanges;

import api.model.ApiInstrument;
import api.model.InstrumentWitdrawalFee;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public interface BittrexApi {
    String BITTREX_URL = "https://bittrex.com/api/v1.1";

    @RequestLine(requestLine = BITTREX_URL + "/public/getmarketsummaries")
    List<ApiInstrument> getInstruments();

    @RequestLine(requestLine = BITTREX_URL + "/public/getcurrencies ")
    List<InstrumentWitdrawalFee> getWitdrawalFees();
}
