package api;

import api.exchanges.BittrexApi;
import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import core.MathHelper;
import core.model.Exchange;
import core.model.Instrument;
import core.model.CoinInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BittrexManagerImpl implements BittrexManager {
    private final BittrexApi bittrexApi;

    @Inject
    public BittrexManagerImpl(final BittrexApi bittrexApi) {
        this.bittrexApi = bittrexApi;
    }

    @Override
    public List<Instrument> getInstruments() {
        List<Instrument> ret = new ArrayList<>();
        List<ApiInstrument> apiInstruments = bittrexApi.getInstruments();
        for (ApiInstrument apiInstrument : apiInstruments) {
            List<Instrument> unpackedInstruments = ApiDataObjectHelper.unpackApiInstrument(
                    apiInstrument,
                    getIBuyFee(apiInstrument),
                    getISellFee(apiInstrument),
                    this.getExchange());

            ret.addAll(unpackedInstruments);
        }

        return Collections.unmodifiableList(ret);
    }

    @Override
    public Map<String, CoinInfo> getInstrumentInfo() {
        List<ApiInstrumentInfo> infoList = bittrexApi.getInstrumentsInfo();
        return ApiDataObjectHelper.toInstrumentInfoMap(infoList, this.getExchange());
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BITTREX;
    }

    private double getIBuyFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getIBuyPriceNoFee());
    }

    private double getISellFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getISellPriceNoFee());
    }
}
