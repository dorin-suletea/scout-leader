package api;

import api.exchanges.BinanceApi;
import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import core.MathHelper;
import core.model.Exchange;
import core.model.Instrument;
import core.model.InstrumentInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BinanceManagerImpl implements BinanceManager {
    private final BinanceApi binanceApi;

    @Inject
    public BinanceManagerImpl(final BinanceApi binanceApi) {
        this.binanceApi = binanceApi;
    }

    @Override
    public List<Instrument> getInstruments() {
        List<Instrument> ret = new ArrayList<>();
        List<ApiInstrument> apiInstruments = binanceApi.getInstruments();
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
    public Map<String, InstrumentInfo> getInstrumentInfo() {
        List<ApiInstrumentInfo> infoList = binanceApi.getInstrumentsInfo();
        return ApiDataObjectHelper.toInstrumentInfoMap(infoList, this.getExchange());
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BINANCE;
    }

    private double getIBuyFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BINANCE.getExchangeFee(), apiInstrument.getIBuyPriceNoFee());
    }

    private double getISellFee(final ApiInstrument apiInstrument) {
        return MathHelper.percentOf(Exchange.BINANCE.getExchangeFee(), apiInstrument.getISellPriceNoFee());
    }
}
