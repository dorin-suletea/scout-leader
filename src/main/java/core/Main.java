package core;

import api.KeyProviderImpl;
import core.model.Exchange;
import core.transaction.TransactionRouter;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {

        String baseCurrency = "USDT";
        double deposit = 500 ;


        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);

        //List<TransactionChainAndChainResult> chains = transactionRouter.getTradeChains(Exchange.BITTREX, baseCurrency, deposit, Config.TRANSFER_STRATEGY_STRATEGY);

        //for (TransactionChainAndChainResult chainAndChainResult : chains){
        //    System.out.println(chainAndChainResult.toDebugString(deposit));
        //}

        System.out.print(
        RuntimeModule.getInjectedObject(KeyProviderImpl.class).getApiKey(Exchange.BINANCE));

    }
}
