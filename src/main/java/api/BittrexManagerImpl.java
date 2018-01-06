package api;

import api.exchanges.ApiHelper;
import api.exchanges.BittrexApi;
import api.model.ApiInstrument;
import api.model.InstrumentInfo;
import core.MathHelper;
import core.model.Exchange;
import core.model.Instrument;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BittrexManagerImpl implements BittrexManager{
    private final BittrexApi bittrexApi;

    @Inject
    public BittrexManagerImpl(final BittrexApi bittrexApi){
        this.bittrexApi = bittrexApi;
    }

    @Override
    public List<Instrument> getPairs() {
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
        bittrexApi.getInstrumentsInfo();
        return null;
    }

    private double getIBuyFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getIBuyPriceNoFee());
    }

    private double getISellFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getISellPriceNoFee());
    }
}
