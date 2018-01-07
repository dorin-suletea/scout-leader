package core.model.transaction;

public class TransactionResult {
    private final String resultCoin;
    private final double coinCount;

    public TransactionResult(final String resultCoin, final double coinCount) {
        this.resultCoin = resultCoin;
        this.coinCount = coinCount;
    }

    public String getResultCoin() {
        return resultCoin;
    }

    public double getCoinCount() {
        return coinCount;
    }
}
