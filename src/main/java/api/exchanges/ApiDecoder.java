package api.exchanges;

import api.model.ApiInstrument;

import java.util.List;

public interface ApiDecoder {
    List<ApiInstrument> decodeBittrexPairs(final String apiResponse);
}
