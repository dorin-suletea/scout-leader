package api.exchanges;

import api.ApiDataObjectHelper;
import api.exchanges.api.BittrexApi;
import api.ApiInstrumentInfo;
import core.model.CoinInfo;
import core.model.Exchange;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class BittrexManagerImpl extends ExchangeManagerBase implements BittrexManager {

    @Inject
    public BittrexManagerImpl(final BittrexApi bittrexApi) {
        super(bittrexApi);
    }

    @Override
    public Map<String, CoinInfo> getCoinInfo() {
        List<ApiInstrumentInfo> infoList = super.exchangeApi.getInstrumentsInfo();
        return ApiDataObjectHelper.toInstrumentInfoMap(infoList, this.getExchange());
    }

    @Override
    public Exchange getExchange() {
        return Exchange.BITTREX;
    }
}
