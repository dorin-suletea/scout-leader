package api.exchanges;

import core.model.Exchange;
import core.model.Instrument;
import core.model.CoinInfo;

import java.util.List;
import java.util.Map;

public interface ExchangeManager {
    List<Instrument> getInstruments();
    Map<String, CoinInfo> getCoinInfo();
    Exchange getExchange();
    double getBalanceForCoin(String coin);
    String getDepositAddress(String coin);
}
