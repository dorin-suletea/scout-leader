package core;

import api.exchanges.BinanceApi;
import api.exchanges.BittrexApi;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {
        int maxChainSize = 3;
        String baseCurrency = "ETH";
        double deposit = 0.3;

        BinanceApi binanceApi = RuntimeModule.getInjectedObject(BinanceApi.class);
        BittrexApi bitrexApi = RuntimeModule.getInjectedObject(BittrexApi.class);
        System.out.println(bitrexApi.getInstruments());
        System.out.println(binanceApi.getInstruments());


//        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
//        List<TransactionChainAndChainResult> possibleChains = transactionRouter.getSingleExchangeTrades(Exchange.BITTREX, maxChainSize, baseCurrency, deposit);
//        for (TransactionChainAndChainResult out : possibleChains) {
//            System.out.println(out);
//        }
    }
}
