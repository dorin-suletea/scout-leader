package core.model.transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionChain implements Transaction {
    private final List<Transaction> chain;

    public TransactionChain() {
        chain = new ArrayList<>();
    }

    public TransactionChain(final List<Transaction> transactions) {
        chain = new ArrayList<>(transactions);
    }

    public void addToChain(final Transaction transactionToAdd) {
        chain.add(transactionToAdd);
    }

    public void addToChain(final List<Transaction> transactionToAdd) {
        chain.addAll(transactionToAdd);
    }

    @Override
    public TransactionResult getTransactionOutput(final double baseCurrencyDeposit) {
        double inputForNextTrade = baseCurrencyDeposit;

        TransactionResult transactionResult = null;
        for (Transaction trade : chain) {
            transactionResult = trade.getTransactionOutput(inputForNextTrade);
            inputForNextTrade = transactionResult.getCoinCount();
        }

        return transactionResult;
    }

    @Override
    public String getSignature() {
        StringBuilder ret = new StringBuilder();
        for (Transaction t : chain) {
            ret.append(t.getSignature());
        }
        return ret.toString();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Transaction transaction : chain) {
            ret.append(transaction.toString() + "\n");
        }
        return ret.toString();
    }
}
