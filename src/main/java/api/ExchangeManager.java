package api;

import core.model.Instrument;

import java.util.List;
import java.util.Map;

public interface ExchangeManager {
    List<Instrument> getPairs();
    Map<String, Double> getWithdrawFeeMap();
}
