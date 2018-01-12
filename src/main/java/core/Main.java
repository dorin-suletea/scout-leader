package core;

import api.CoinBlacklist;
import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;
import core.transaction.TransactionRouter;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {
        CoinBlacklist blacklist = RuntimeModule.getInjectedObject(CoinBlacklist.class);
        blacklist.blackListCoins(Exchange.BINANCE,"GNT");
        blacklist.blackListCoins(Exchange.BINANCE,"CVC");
        blacklist.blackListCoins(Exchange.BINANCE,"GNO");
        blacklist.blackListCoins(Exchange.BINANCE,"REP");
        blacklist.blackListCoins(Exchange.BINANCE,"SC");
        blacklist.blackListCoins(Exchange.BINANCE,"XEM");
        blacklist.blackListCoins(Exchange.BINANCE,"STEEM");
        blacklist.blackListCoins(Exchange.BINANCE,"DGB");
        blacklist.blackListCoins(Exchange.BINANCE,"FCT");

        blacklist.blackListCoins(Exchange.BITTREX,"ARDR");

        String baseCurrency = "ETH";
        double deposit = 1;


        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
        List<TransactionChainAndChainResult> chains = transactionRouter.getTradeChains(Exchange.BINANCE, baseCurrency, deposit);

        for (TransactionChainAndChainResult chainAndChainResult : chains){
            System.out.println(chainAndChainResult.toDebugString(deposit));
        }
    }
}
