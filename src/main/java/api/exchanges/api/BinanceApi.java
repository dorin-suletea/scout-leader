package api.exchanges.api;

public interface BinanceApi extends ExchangeApi {
    String BINANCE_URL = "https://www.binance.com/api/";
    String INSTRUMENTS_URL = BINANCE_URL + "v1/ticker/allBookTickers";
    String TIME_URL = BINANCE_URL + "v1/time";
    String ASSETS_URL = BINANCE_URL + "v3/account";
    String DEPOSIT_API = "https://api.binance.com/wapi/v3/depositAddress.html";
}
