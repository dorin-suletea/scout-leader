package api.exchanges;

import api.model.ApiInstrument;
import api.model.InstrumentWitdrawalFee;
import feign.RequestLine;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public interface BittrexApi {
    String BITTREX_URL = "https://bittrex.com/api/v1.1";

    @RequestLine("GET /public/getmarketsummaries")
    List<ApiInstrument> getInstruments();

    @RequestLine("GET /public/getcurrencies ")
    List<InstrumentWitdrawalFee> getWitdrawalFees();
}
