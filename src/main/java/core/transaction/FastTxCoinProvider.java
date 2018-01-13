package core.transaction;

import java.util.List;

public interface FastTxCoinProvider {
    List<String> getFastTransactionCoins();
    void addFastTransactionCoin(final String coin);
    void addFastTransactionCoin(final String ...coins);
}
