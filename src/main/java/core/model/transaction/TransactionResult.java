package core.model.transaction;

import core.StringHelper;

public class TransactionResult {
    private final String resultCoin;
    private final Double coinCount;

    public TransactionResult(final String resultCoin, final Double coinCount) {
        this.resultCoin = resultCoin;
        this.coinCount = coinCount;
    }

    public String getResultCoin() {
        return resultCoin;
    }

    public Double getCoinCount() {
        return coinCount;
    }

    @Override
    public String toString() {
        return "(" + StringHelper.formattedDouble(getCoinCount()) + " " + getResultCoin() + ")";
    }
}
