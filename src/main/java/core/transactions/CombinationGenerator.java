package core.transactions;

import core.model.Instrument;

import java.util.List;

public interface CombinationGenerator {
    List<List<Instrument>> getCombinations(final int maxChainSize,
                                           final List<Instrument> instrumentList,
                                           final String baseCurrency);
}
