package api;

import java.util.Date;

public class TimeProviderImpl implements TimeProvider {
    private long localTime;
    private long timeOffset;

    public TimeProviderImpl() {
        localTime = new Date().getTime();
    }


    @Override
    public void synchronize(final long tsInMillis) {
        timeOffset = localTime - tsInMillis;
    }

    @Override
    public long getCurrentMillis() {
        return localTime + timeOffset;
    }
}
