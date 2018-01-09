package api;

import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import core.model.Exchange;
import core.model.Instrument;
import core.model.InstrumentDirection;
import core.model.InstrumentInfo;

import java.util.*;

public class ApiDataObjectHelper {
    public static InstrumentInfo unpackApiInstrumentInfo(final ApiInstrumentInfo apiInstrumentInfo, final Exchange exchange) {
        return new InstrumentInfo(apiInstrumentInfo.getSymbol(), apiInstrumentInfo.getWithdrawalFee(), apiInstrumentInfo.isActive(), exchange);
    }

    public static Map<String, InstrumentInfo> toInstrumentInfoMap(final List<ApiInstrumentInfo> instrumentInfoList, final Exchange exchange) {
        Map<String, InstrumentInfo> ret = new HashMap<>();
        for (ApiInstrumentInfo apiInfo : instrumentInfoList) {
            InstrumentInfo unpackedInfo = ApiDataObjectHelper.unpackApiInstrumentInfo(apiInfo, exchange);
            ret.put(apiInfo.getSymbol(), unpackedInfo);

        }
        return Collections.unmodifiableMap(ret);
    }

    public static List<Instrument> unpackApiInstrument(final ApiInstrument apiInstrument,
                                                       final double iBuyTradeFee,
                                                       final double iSellTradeFee,
                                                       final Exchange exchange) {
        final Double iBuyPriceNoFee = apiInstrument.getIBuyPriceNoFee();
        final Double iSellPriceNoFee = apiInstrument.getISellPriceNoFee();

        final Double iBuyPrice = iBuyPriceNoFee + iBuyTradeFee;
        final Double iSellPrice = iSellPriceNoFee - iSellTradeFee;

        final Instrument buyInstrument = new Instrument(
                apiInstrument.getLeftSymbol(),
                apiInstrument.getRightSymbol(),
                iBuyPrice,
                InstrumentDirection.BUY,
                exchange);

        final Instrument sellInstrument = new Instrument(
                apiInstrument.getRightSymbol(),
                apiInstrument.getLeftSymbol(),
                1 / iSellPrice,
                InstrumentDirection.SELL,
                exchange);


        List<Instrument> ret = new ArrayList<>();
        ret.add(buyInstrument);
        ret.add(sellInstrument);
        return ret;
    }
}
