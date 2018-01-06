package core.model;

public class InstrumentInfo {
    private final String symbol;
    private final double withdrawalFee;
    private final boolean isActive;
    private final Exchange exchange;

    public InstrumentInfo(final String symbol, final double withdrawalFee, final boolean isActive, final Exchange exchange) {
        this.symbol = symbol;
        this.withdrawalFee = withdrawalFee;
        this.isActive = isActive;
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getWithdrawalFee() {
        return withdrawalFee;
    }

    @Override
    public String toString() {
        return "InstrumentInfo{" +
                "symbol='" + symbol + '\'' +
                ", withdrawalFee=" + withdrawalFee +
                ", isActive=" + isActive +
                ", exchange=" + exchange +
                '}';
    }
}
