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
        int maxChainSize = 3;
        String baseCurrency = "ETH";
        double deposit = 0.3;

        TransactionRouter transactionRouter = RuntimeModule.getInjectedObject(TransactionRouter.class);
        List<TransactionChainAndChainResult> possibleChains = transactionRouter.getSingleExchangeTrades(Exchange.BITTREX, maxChainSize, baseCurrency, deposit);
        for (TransactionChainAndChainResult out : possibleChains) {
            System.out.println(out);
        }
    }
}
