package core.model.transaction;

import core.model.Exchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransactionChain implements Transaction{
    public static TransactionChain VOID_CHAIN = new TransactionChain();

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

    @Override
    public String getResultCoin() {
        return transactions.get(transactions.size()-1).getResultCoin();
    }

    @Override
    public String getInputCoin() {
        return transactions.get(0).getInputCoin();
    }

    @Override
    public Exchange getResultExchange() {
        return transactions.get(transactions.size()-1).getResultExchange();
    }

    public List<Transaction> flattren(){
        final List<Transaction> ret = new ArrayList<>();
        for (Transaction transaction : transactions){
            if (transaction instanceof TransactionChain){
                ret.addAll(((TransactionChain)transaction).flattren());
            }else{
                ret.add(transaction);
            }
        }
        return ret;
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

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Transaction chainTransactions : this.transactions){
            ret.append(chainTransactions.toString());
        }
        return ret.toString();
    }

    public boolean isValidChain() {
        if (transactions.isEmpty()){
            return false;
        }

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
