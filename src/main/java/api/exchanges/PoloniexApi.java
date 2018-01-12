package api.exchanges;

public interface PoloniexApi extends ExchangeApi {
    String POLONIEX_URL = "https://poloniex.com/public?command=";
    String INSTRUMENTS_URL = POLONIEX_URL + "returnTicker";
    String COIN_INFO_URL = POLONIEX_URL + "returnCurrencies";

}
