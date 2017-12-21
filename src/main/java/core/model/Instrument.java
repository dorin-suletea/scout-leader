package core.model;

/**
 * Created by next on 12/15/17.
 */
public class Instrument {
    private final String leftSymbol;
    private final String rightSymbol;
    private final Double price;
    private final InstrumentDirection instrumentDirection;
    private final Exchange exchange;

    public Instrument(final String leftSymbol, final String rightSymbol, final Double price, final InstrumentDirection instrumentDirection, final Exchange exchange) {
        this.leftSymbol = leftSymbol;
        this.rightSymbol = rightSymbol;
        this.exchange = exchange;
        this.price = price;
        this.instrumentDirection = instrumentDirection;
    }


    //think about reverse
//    public Instrument reversePair() {
//        //raw price convert by canceling out the fee applied
//        final double iBuyPrice = 1/ (iSellPriceNoFee + MathHelper.percentOf(this.exchange.getExchangeFee(), iSellPriceNoFee));
//        final double iSellPrice = 1/ (iBuyPriceNoFee - MathHelper.percentOf(this.exchange.getExchangeFee(), iBuyPriceNoFee));
//
//
//        return new Instrument(rightSymbol, leftSymbol, iBuyPrice, iSellPrice, this.exchange, iSellPriceNoFee, iBuyPriceNoFee);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instrument instrument = (Instrument) o;

        if (!leftSymbol.equals(instrument.leftSymbol)) return false;
        if (!rightSymbol.equals(instrument.rightSymbol)) return false;
        return exchange == instrument.exchange;
    }

    @Override
    public int hashCode() {
        int result = leftSymbol.hashCode();
        result = 31 * result + rightSymbol.hashCode();
        result = 31 * result + exchange.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "leftSymbol='" + leftSymbol + '\'' +
                ", rightSymbol='" + rightSymbol + '\'' +
                ", exchange=" + exchange +
                ", price=" + price +
                ", instrumentDirection=" + instrumentDirection +
                '}';
    }
}
