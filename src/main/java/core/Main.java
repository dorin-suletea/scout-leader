package core;

import core.model.Exchange;
import core.model.transaction.TransactionChainAndChainResult;
import core.transaction.TransactionRouter;

import java.util.List;

/**
 * Created by next on 12/20/17.
 */
public class Main {
    public static void main(String args[]) {
        String baseCurrency = "XRP";
        double deposit = 100;


        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
        List<TransactionChainAndChainResult> chains = transactionRouter.getTradeChains(Exchange.BINANCE, baseCurrency, deposit);

        for (TransactionChainAndChainResult chainAndChainResult : chains){
            System.out.println(chainAndChainResult.toDebugString(deposit));
        }
    }
}
