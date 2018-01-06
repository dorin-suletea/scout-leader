package api.model;

public class InstrumentInfo {
    private final String symbol;
    private final double withdrawalFee;
    private final boolean isActive;

    public InstrumentInfo(final String symbol, final double withdrawalFee, final boolean isActive) {
        this.symbol = symbol;
        this.withdrawalFee = withdrawalFee;
        this.isActive = isActive;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }
}
