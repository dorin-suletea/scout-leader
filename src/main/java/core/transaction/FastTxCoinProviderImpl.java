package core.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastTxCoinProviderImpl implements FastTxCoinProvider{
    private final List<String> fastTxCoins;

    public FastTxCoinProviderImpl() {
        fastTxCoins = new ArrayList<>();
    }

    @Override
    public List<String> getFastTransactionCoins() {
        return Collections.unmodifiableList(fastTxCoins);
    }

    @Override
    public void addFastTransactionCoin(final String coin) {
        if (!fastTxCoins.contains(coin)) {
            fastTxCoins.add(coin);
        }
    }

    @Override
    public void addFastTransactionCoin(final String... coins) {
        for (int i=0;i<coins.length;i++){
            fastTxCoins.add(coins[i]);
        }
    }
}
