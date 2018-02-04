package api.exchanges.api;

public interface TimeProvider {
    void synchronize(long tsInMillis);
    long getCurrentMillis();
}
