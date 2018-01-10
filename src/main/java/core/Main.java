package core;

import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;
import core.transactions.TransactionRouter;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {
        String baseCurrency = "ETH";
        double deposit = 1;

//        BinanceApi binanceApi = RuntimeModule.getInjectedObject(BinanceApi.class);
//        BittrexApi bitrexApi = RuntimeModule.getInjectedObject(BittrexApi.class);
//        System.out.println(bitrexApi.getInstruments());
//        System.out.println(binanceApi.getInstruments());


        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
        List<TransactionChainAndChainResult> chains = transactionRouter.getTradeChains(Exchange.BITTREX, "ETH", 1d);

        for (TransactionChainAndChainResult chainAndChainResult : chains){
            System.out.println(chainAndChainResult.toDebugString(deposit));
        }



//        List<TransactionChainAndChainResult> possibleChains = transactionRouter.getSingleExchangeTrades(Exchange.BITTREX, maxChainSize, baseCurrency, deposit);
//        for (TransactionChainAndChainResult out : possibleChains) {
//            System.out.println(out);
//        }
    }
}
