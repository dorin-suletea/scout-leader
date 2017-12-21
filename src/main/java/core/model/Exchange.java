package core.model;

public enum Exchange {
    BINANCE( 0.1d),
    BITTREX( 0.25d);

    private final double exchangeFee;

    Exchange(final double exchangeFee) {
        this.exchangeFee = exchangeFee;
    }

    public double getExchangeFee() {
        return exchangeFee;
    }
}