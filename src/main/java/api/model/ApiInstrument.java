package api.model;

public class ApiInstrument {
    private final String leftSymbol;
    private final String rightSymbol;
    private final Double iBuyPriceNoFee;
    private final Double iSellPriceNoFee;

    public ApiInstrument(final String leftSymbol, final String rightSymbol, final Double iBuyPriceNoFee, final Double iSellPriceNoFee) {
        this.leftSymbol = leftSymbol;
        this.rightSymbol = rightSymbol;
        this.iBuyPriceNoFee = iBuyPriceNoFee;
        this.iSellPriceNoFee = iSellPriceNoFee;
    }

    public String getLeftSymbol() {
        return leftSymbol;
    }

    public String getRightSymbol() {
        return rightSymbol;
    }

    public Double getIBuyPriceNoFee() {
        return iBuyPriceNoFee;
    }

    public Double getISellPriceNoFee() {
        return iSellPriceNoFee;
    }

}
