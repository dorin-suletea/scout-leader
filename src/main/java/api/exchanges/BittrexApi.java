package api.exchanges;

import api.model.ApiPair;
import feign.RequestLine;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public interface BittrexApi {
    String BITTREX_URL = "https://bittrex.com/api/v1.1";

    @RequestLine("GET /public/getmarketsummaries")
    List<ApiPair> getMarketSummaries();
}
