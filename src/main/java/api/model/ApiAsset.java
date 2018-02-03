package api.model;

public class ApiAsset {
    private final String coin;
    private final double quantity;

    public ApiAsset(final String coin, final double quantity) {
        this.coin = coin;
        this.quantity = quantity;
    }

    public String getCoin() {
        return coin;
    }

    public double getQuantity() {
        return quantity;
    }
}
