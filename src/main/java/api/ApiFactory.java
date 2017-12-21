package api;

import api.exchanges.BittrexApi;
import feign.Feign;
import feign.codec.Decoder;
import feign.jackson.JacksonEncoder;

/**
 * Created by next on 12/21/17.
 */
public class ApiFactory {
    private final ApiDecoderProvider apiDecoderProvider;

    public ApiFactory(final ApiDecoderProvider apiDecoderProvider) {
        this.apiDecoderProvider = apiDecoderProvider;
    }

    public BittrexApi makeBittrexApi(){
        Decoder dec = (response, type) -> {
            String apiResponse = ApiHelper.getStreamContent(response.body().asInputStream());
            return apiDecoderProvider.decodeBittrexPairs(apiResponse);
        };

        BittrexApi bittrexApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(dec)
                .target(BittrexApi.class, BittrexApi.BITTREX_URL);
        return bittrexApi;
    }
}
