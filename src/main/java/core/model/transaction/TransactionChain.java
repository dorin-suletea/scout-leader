package core.model.transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionChain{
    private final List<Transaction> transactions;

    public TransactionChain() {
        transactions = new ArrayList<>();
    }

    public TransactionChain(final TransactionChain... subChains) {
        this.transactions = new ArrayList<>();
        addToChain(subChains);
    }

    public TransactionChain(final List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
    }

    public TransactionChain(final Transaction... transactions) {
        this.transactions = new ArrayList<>(Arrays.asList(transactions));
    }

    public void addToChain(final Transaction transactionToAdd) {
        transactions.add(transactionToAdd);
    }

    public void addToChain(final TransactionChain... subChains) {
        for (int i=0;i<subChains.length;i++){
            transactions.addAll(subChains[i].transactions);
        }
    }

    public void addToChain(final List<Transaction> transactionToAdd) {
        transactions.addAll(transactionToAdd);
    }

    public void addToChain(final Transaction... transactionsToAdd) {
        transactions.addAll(Arrays.asList(transactionsToAdd));
    }

    public TransactionResult getTransactionOutput(final double baseCurrencyDeposit) {
        double inputForNextTrade = baseCurrencyDeposit;

        TransactionResult transactionResult = null;
        for (Transaction transaction : transactions) {
            transactionResult = transaction.getTransactionOutput(inputForNextTrade);
            inputForNextTrade = transactionResult.getCoinCount();
        }

        return transactionResult;
    }

    public String getSignature() {
        StringBuilder ret = new StringBuilder();
        for (Transaction t : transactions) {
            ret.append(t.getSignature());
        }
        return ret.toString();
    }


    public String toDebugString(final double inputCoinCount) {
        StringBuilder ret = new StringBuilder();
        double inputForNextTrade = inputCoinCount;
        TransactionResult transactionResult;

        for (Transaction transaction : transactions) {
            transactionResult = transaction.getTransactionOutput(inputForNextTrade);
            inputForNextTrade = transactionResult.getCoinCount();
            ret.append(transaction.toString() + " " + transactionResult + " " + "\n");
        }
        return ret.toString();
    }

    public boolean isValidChain() {
        for (Transaction transaction : transactions) {
            if (transaction == null) {
                return false;
            }
        }
        return true;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
