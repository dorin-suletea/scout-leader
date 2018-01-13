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
        init();
    }
    
    private void init(){
        this.blackListCoins(Exchange.BINANCE,"GNT");
        this.blackListCoins(Exchange.BINANCE,"CVC");
        this.blackListCoins(Exchange.BINANCE,"GNO");
        this.blackListCoins(Exchange.BINANCE,"REP");
        this.blackListCoins(Exchange.BINANCE,"SC");
        this.blackListCoins(Exchange.BINANCE,"XEM");
        this.blackListCoins(Exchange.BINANCE,"STEEM");
        this.blackListCoins(Exchange.BINANCE,"DGB");
        this.blackListCoins(Exchange.BINANCE,"FCT");
        this.blackListCoins(Exchange.BINANCE,"AMP");

        this.blackListCoins(Exchange.BINANCE,"ARDR");
        this.blackListCoins(Exchange.BINANCE,"BCY");
        this.blackListCoins(Exchange.BINANCE,"BLK");
        this.blackListCoins(Exchange.BINANCE,"BTCD");
        this.blackListCoins(Exchange.BINANCE,"BURST");
        this.blackListCoins(Exchange.BINANCE,"CLAM");
        this.blackListCoins(Exchange.BINANCE,"DCR");
        this.blackListCoins(Exchange.BINANCE,"DOGE");
        this.blackListCoins(Exchange.BINANCE,"EMC2");
        this.blackListCoins(Exchange.BINANCE,"EXP");
        this.blackListCoins(Exchange.BINANCE,"FLDC");
        this.blackListCoins(Exchange.BINANCE,"FLO");
        this.blackListCoins(Exchange.BINANCE,"GAME");
        this.blackListCoins(Exchange.BINANCE,"GRC");
        this.blackListCoins(Exchange.BINANCE,"LBC");
        this.blackListCoins(Exchange.BINANCE,"MAID");
        this.blackListCoins(Exchange.BINANCE,"NEOS");
        this.blackListCoins(Exchange.BINANCE,"NXC");
        this.blackListCoins(Exchange.BINANCE,"NXT");
        this.blackListCoins(Exchange.BINANCE,"OMNI");
        this.blackListCoins(Exchange.BINANCE,"PINK");
        this.blackListCoins(Exchange.BINANCE,"POT");
        this.blackListCoins(Exchange.BINANCE,"PPC");
        this.blackListCoins(Exchange.BINANCE,"RADS");
        this.blackListCoins(Exchange.BINANCE,"SBD");
        this.blackListCoins(Exchange.BINANCE,"SYS");
        this.blackListCoins(Exchange.BINANCE,"VIA");
        this.blackListCoins(Exchange.BINANCE,"VRC");
        this.blackListCoins(Exchange.BINANCE,"VTC");
        this.blackListCoins(Exchange.BINANCE,"XCP");
        this.blackListCoins(Exchange.BINANCE,"XVC");
        this.blackListCoins(Exchange.BINANCE,"PIVX");


        this.blackListCoins(Exchange.BITTREX,"GAS");
        this.blackListCoins(Exchange.BITTREX,"ARDR");
        this.blackListCoins(Exchange.BITTREX,"ZRX");
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
