import core.Constants;
import core.model.transaction.TransactionChain;

import java.util.Arrays;
import java.util.List;

public class TestHelper {

    public static List<String> splitChainIntoTransactionSignatures(final TransactionChain chain){
        return Arrays.asList(chain.getSignature().split(Constants.TRANSACTION_SIGNATURE_DELIMITER));
    }
}
