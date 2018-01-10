package core.model;

public class CoinInfo {
    private final String coin;
    private final double withdrawalFee;
    private final boolean isActive;
    private final Exchange exchange;

    public CoinInfo(final String coin, final double withdrawalFee, final boolean isActive, final Exchange exchange) {
        this.coin = coin;
        this.withdrawalFee = withdrawalFee;
        this.isActive = isActive;
        this.exchange = exchange;
    }

    public String getCoin() {
        return coin;
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    @Override
    public String toString() {
        return "CoinInfo{" +
                "coin='" + coin + '\'' +
                ", withdrawalFee=" + withdrawalFee +
                ", isActive=" + isActive +
                ", exchange=" + exchange +
                '}';
    }
}
