package api;

import api.model.ApiInstrument;
import core.model.Exchange;

public interface CoinBlacklist {
    void blackListCoins(final Exchange exchange, final String coin);
    boolean isCoinBlackListed(final Exchange exchange, final String coin);
    boolean isCoinBlackListed(final Exchange exchange, final ApiInstrument instrument);
}
