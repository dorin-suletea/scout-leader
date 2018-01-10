package core.model.transaction;

import java.util.Comparator;

public class TransactionChainAndChainResult {
    private final TransactionChain chain;
    private final TransactionResult chainRunResult;

    public TransactionChainAndChainResult(final TransactionChain chain, final TransactionResult chainRunResult) {
        this.chain = chain;
        this.chainRunResult = chainRunResult;
    }

    public TransactionChain getChain() {
        return chain;
    }

    public TransactionResult getChainRunResult() {
        return chainRunResult;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("-----------------------------\n");
        ret.append(chain.toString()+"\n");
        ret.append("Result : " + chainRunResult.getCoinCount()+ " " + chainRunResult.getResultCoin());
        return ret.toString();
    }

    public static class TransactionChainAndChainResultComparator implements Comparator<TransactionChainAndChainResult> {
        @Override
        public int compare(TransactionChainAndChainResult o1, TransactionChainAndChainResult o2) {
            if (o1.getChainRunResult().getCoinCount() == o2.getChainRunResult().getCoinCount()) {
                return 0;
            }
            if (o1.getChainRunResult().getCoinCount() > o2.getChainRunResult().getCoinCount()) {
                return 1;
            }
            return -1;
        }
    }
}
