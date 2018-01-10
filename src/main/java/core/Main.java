package core;

import core.model.Exchange;
import core.transactions.TransactionRouter;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {
        String baseCurrency = "ETH";
        double deposit = 0.3;

//        BinanceApi binanceApi = RuntimeModule.getInjectedObject(BinanceApi.class);
//        BittrexApi bitrexApi = RuntimeModule.getInjectedObject(BittrexApi.class);
//        System.out.println(bitrexApi.getInstruments());
//        System.out.println(binanceApi.getInstruments());


        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
        System.out.print(transactionRouter.getTradeChains(Exchange.BITTREX,"ETH",1d));
//        List<TransactionChainAndChainResult> possibleChains = transactionRouter.getSingleExchangeTrades(Exchange.BITTREX, maxChainSize, baseCurrency, deposit);
//        for (TransactionChainAndChainResult out : possibleChains) {
//            System.out.println(out);
//        }
    }
}
