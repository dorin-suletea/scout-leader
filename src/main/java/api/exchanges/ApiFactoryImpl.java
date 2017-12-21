package api.exchanges;

import feign.Feign;
import feign.codec.Decoder;
import feign.jackson.JacksonEncoder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApiFactoryImpl implements ApiFactory{
    private final ApiDecoderImpl apiDecoderProvider;

    @Inject
    public ApiFactoryImpl(final ApiDecoderImpl apiDecoderProvider) {
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
