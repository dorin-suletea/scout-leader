package core;

import api.CoinBlacklist;
import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;
import core.transaction.TransactionRouter;
import core.transaction.strategy.TransferTransactionFactory;

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
        blacklist.blackListCoins(Exchange.BINANCE,"AMP");

        blacklist.blackListCoins(Exchange.BINANCE,"ARDR");
        blacklist.blackListCoins(Exchange.BINANCE,"BCY");
        blacklist.blackListCoins(Exchange.BINANCE,"BLK");
        blacklist.blackListCoins(Exchange.BINANCE,"BTCD");
        blacklist.blackListCoins(Exchange.BINANCE,"BURST");
        blacklist.blackListCoins(Exchange.BINANCE,"CLAM");
        blacklist.blackListCoins(Exchange.BINANCE,"DCR");
        blacklist.blackListCoins(Exchange.BINANCE,"DOGE");
        blacklist.blackListCoins(Exchange.BINANCE,"EMC2");
        blacklist.blackListCoins(Exchange.BINANCE,"EXP");
        blacklist.blackListCoins(Exchange.BINANCE,"FLDC");
        blacklist.blackListCoins(Exchange.BINANCE,"FLO");
        blacklist.blackListCoins(Exchange.BINANCE,"GAME");
        blacklist.blackListCoins(Exchange.BINANCE,"GRC");
        blacklist.blackListCoins(Exchange.BINANCE,"LBC");
        blacklist.blackListCoins(Exchange.BINANCE,"MAID");
        blacklist.blackListCoins(Exchange.BINANCE,"NEOS");
        blacklist.blackListCoins(Exchange.BINANCE,"NXC");
        blacklist.blackListCoins(Exchange.BINANCE,"NXT");
        blacklist.blackListCoins(Exchange.BINANCE,"OMNI");
        blacklist.blackListCoins(Exchange.BINANCE,"PINK");
        blacklist.blackListCoins(Exchange.BINANCE,"POT");
        blacklist.blackListCoins(Exchange.BINANCE,"PPC");
        blacklist.blackListCoins(Exchange.BINANCE,"RADS");
        blacklist.blackListCoins(Exchange.BINANCE,"SBD");
        blacklist.blackListCoins(Exchange.BINANCE,"SYS");
        blacklist.blackListCoins(Exchange.BINANCE,"VIA");
        blacklist.blackListCoins(Exchange.BINANCE,"VRC");
        blacklist.blackListCoins(Exchange.BINANCE,"VTC");
        blacklist.blackListCoins(Exchange.BINANCE,"XCP");
        blacklist.blackListCoins(Exchange.BINANCE,"XVC");


        blacklist.blackListCoins(Exchange.BITTREX,"ARDR");

        String baseCurrency = "ETH";
        double deposit = 1;


        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
        TransferTransactionFactory transferTransactionFactory = RuntimeModule.getInjectedObject(TransferTransactionFactory.class);

        List<TransactionChainAndChainResult> chains = transactionRouter.getTradeChains(Exchange.BINANCE, baseCurrency, deposit);

        for (TransactionChainAndChainResult chainAndChainResult : chains){
            System.out.println(chainAndChainResult.toDebugString(deposit));
        }
    }
}
