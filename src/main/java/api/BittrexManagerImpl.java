package api;

import api.exchanges.ApiHelper;
import api.exchanges.BittrexApi;
import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import core.MathHelper;
import core.model.Exchange;
import core.model.Instrument;
import core.model.InstrumentInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BittrexManagerImpl implements BittrexManager{
    private final BittrexApi bittrexApi;

    @Inject
    public BittrexManagerImpl(final BittrexApi bittrexApi){
        this.bittrexApi = bittrexApi;
    }

    @Override
    public List<Instrument> getInstruments() {
        List<Instrument> ret = new ArrayList<>();
        List<ApiInstrument> apiInstruments = bittrexApi.getInstruments();
        for (ApiInstrument apiInstrument : apiInstruments) {
            List<Instrument> unpackedInstruments = ApiHelper.unpackApiInstrument(
                    apiInstrument,
                    getIBuyFee(apiInstrument),
                    getISellFee(apiInstrument),
                    Exchange.BITTREX);

            ret.addAll(unpackedInstruments);
        }

        return ret;
    }

    @Override
    public Map<String, InstrumentInfo> getInstrumentInfo() {
        List<ApiInstrumentInfo> infoList = bittrexApi.getInstrumentsInfo();
        Map<String,InstrumentInfo> ret = new HashMap<>();

        for (ApiInstrumentInfo apiInfo : infoList){
            InstrumentInfo unpackedInfo = ApiHelper.unpackApiInstrumentInfo(apiInfo,Exchange.BITTREX);
            ret.put(apiInfo.getSymbol(),unpackedInfo);

        }
        return ret;
    }

    private double getIBuyFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getIBuyPriceNoFee());
    }

    private double getISellFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getISellPriceNoFee());
    }
}
