package api.exchanges;

public interface BinanceApi extends ExchangeApi {
    String BINANCE_URL = "https://www.binance.com/api/";
    String INSTRUMENTS_URL = BINANCE_URL + "v1/ticker/allBookTickers";
}
