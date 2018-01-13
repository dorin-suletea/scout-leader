package api.exchanges;

import api.ApiDataObjectHelper;
import api.exchanges.api.ExchangeApi;
import api.ApiInstrument;
import core.MathHelper;
import core.model.Instrument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ExchangeManagerBase implements ExchangeManager {
    final ExchangeApi exchangeApi;

    protected ExchangeManagerBase(final ExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }


    @Override
    public List<Instrument> getInstruments() {
        List<Instrument> ret = new ArrayList<>();
        List<ApiInstrument> apiInstruments = exchangeApi.getInstruments();
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


    private double getIBuyFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(this.getExchange().getExchangeFee(), apiInstrument.getIBuyPriceNoFee());
    }

    private double getISellFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(this.getExchange().getExchangeFee(), apiInstrument.getISellPriceNoFee());
    }
}
