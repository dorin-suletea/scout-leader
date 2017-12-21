package api.model;

public class InstrumentWitdrawalFee {
    private final String symbol;
    private final double witdrawalFee;

    public InstrumentWitdrawalFee(final String symbol, final double witdrawalFee) {
        this.symbol = symbol;
        this.witdrawalFee = witdrawalFee;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getWitdrawalFee() {
        return witdrawalFee;
    }
}
