package api.exchanges.api;

import java.util.Date;

public class TimeProviderImpl implements TimeProvider {
    private long initialLocalTime;
    private long initialServerTime;

    @Override
    public void synchronize(final long tsInMillis) {
        initialLocalTime = new Date().getTime();
        initialServerTime = tsInMillis;
    }

    @Override
    public long getCurrentMillis() {
        return initialServerTime + new Date().getTime() - initialLocalTime;
    }
}
