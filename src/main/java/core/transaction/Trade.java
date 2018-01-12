package core.transaction;

import core.MathHelper;
import core.model.Instrument;

import java.util.Comparator;

public class Trade {
    private final Instrument from;
    private final Instrument to;

    public Trade(final Instrument from, final Instrument to) {
        this.from = from;
        this.to = to;
    }

    public Double getTradeProfitPercentage() {
        return MathHelper.percentGain(to.getPrice() - from.getPrice(), from.getPrice());
    }

    public Instrument getFrom() {
        return from;
    }

    public Instrument getTo() {
        return to;
    }


    public static class TradeComparator implements Comparator<Trade> {
        @Override
        public int compare(Trade o1, Trade o2) {
            if (o1.getTradeProfitPercentage() == o2.getTradeProfitPercentage()) {
                return 0;
            }
            if (o1.getTradeProfitPercentage() > o2.getTradeProfitPercentage()) {
                return 1;
            }
            return -1;
        }
    }
}
