package api.exchanges;

import api.ApiDataObjectHelper;
import api.TimeProvider;
import api.exchanges.api.BinanceApi;
import api.model.ApiInstrumentInfo;
import core.model.CoinInfo;
import core.model.Exchange;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class BinanceManagerImpl extends ExchangeManagerBase implements BinanceManager {


    @Inject
    public BinanceManagerImpl(final BinanceApi binanceApi, final TimeProvider timeProvider) {
        super(binanceApi);
    }

    @Override
    public Map<String, CoinInfo> getCoinInfo() {
        List<ApiInstrumentInfo> infoList = super.exchangeApi.getInstrumentsInfo();
        return ApiDataObjectHelper.toInstrumentInfoMap(infoList, this.getExchange());
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BINANCE;
    }
}
