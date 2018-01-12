package api;

import api.model.ApiInstrument;
import core.model.Exchange;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class CoinBlacklistImpl implements CoinBlacklist {
    private final Map<Exchange, List<String>> blacklistCoins;


    public CoinBlacklistImpl() {
        blacklistCoins = new HashMap<>();
    }


    public void blackListCoins(final Exchange exchange, final String coin) {
        if (!blacklistCoins.containsKey(exchange)) {
            blacklistCoins.put(exchange, new ArrayList<>(Collections.singleton(coin)));
            return;
        }

        List<String> exchangeBlacklist = blacklistCoins.get(exchange);
        exchangeBlacklist.add(coin);
    }

    public boolean isCoinBlackListed(final Exchange exchange, final String coin) {
        if (!blacklistCoins.containsKey(exchange)) {
            return false;
        }
        return blacklistCoins.get(exchange).contains(coin);
    }

    @Override
    public boolean isCoinBlackListed(final Exchange exchange, final ApiInstrument instrument) {
        if (!blacklistCoins.containsKey(exchange)) {
            return false;
        }
        List<String> exchangeBlacklist = blacklistCoins.get(exchange);
        boolean isLeftBlacklisted = exchangeBlacklist.contains(instrument.getLeftSymbol());
        boolean isRightBlacklisted = exchangeBlacklist.contains(instrument.getRightSymbol());

        return isLeftBlacklisted || isRightBlacklisted;
    }

}
