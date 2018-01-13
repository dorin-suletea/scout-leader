package api;

public class ApiInstrumentInfo {
    private final String coin;
    private final double withdrawalFee;
    private final boolean isActive;

    public ApiInstrumentInfo(final String coin, final double withdrawalFee, final boolean isActive) {
        this.coin = coin;
        this.withdrawalFee = withdrawalFee;
        this.isActive = isActive;
    }

    public String getCoin() {
        return coin;
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    public boolean isActive() {
        return isActive;
    }
}
