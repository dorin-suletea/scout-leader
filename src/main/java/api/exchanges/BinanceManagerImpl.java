package api.exchanges;

import api.ApiDataObjectHelper;
import api.exchanges.api.BinanceApi;
import api.model.ApiAsset;
import api.model.ApiInstrumentInfo;
import core.model.CoinInfo;
import core.model.Exchange;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class BinanceManagerImpl extends ExchangeManagerBase implements BinanceManager {


    @Inject
    public BinanceManagerImpl(final BinanceApi binanceApi) {
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

    @Override
    public double getBalanceForCoin(final String coin) {
        List<ApiAsset> assets = super.exchangeApi.getAssets();
        for (ApiAsset asset : assets) {
            if (asset.getCoin().equals(coin)) {
                return asset.getQuantity();
            }
        }
        throw new RuntimeException("No wallet for coin " + coin + " on exchange " + getExchange());
    }

    @Override
    public String getDepositAddress(final String coin) {
        return super.exchangeApi.getDepositAddress(coin);
    }
}
