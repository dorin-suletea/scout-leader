package api;

import api.exchanges.ApiFactoryImpl;
import api.exchanges.ApiHelper;
import api.model.ApiInstrument;
import api.exchanges.BittrexApi;
import core.MathHelper;
import core.model.Exchange;
import core.model.Instrument;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BittrexManagerImpl implements BittrexManager{
    private final BittrexApi bittrexApi;

    @Inject
    public BittrexManagerImpl(final ApiFactoryImpl apiFactory){
        this.bittrexApi = apiFactory.makeBittrexApi();
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
    public Map<String, Double> getWithdrawFeeMap() {
        return new HashMap<>();
    }

    private double getIBuyFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getIBuyPriceNoFee());
    }

    private double getISellFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BITTREX.getExchangeFee(), apiInstrument.getISellPriceNoFee());
    }
}
