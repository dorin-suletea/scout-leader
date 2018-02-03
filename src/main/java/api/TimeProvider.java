package api;

public interface TimeProvider {
    void setClock(long tsInMillis);
    long getCurrentMillis();
}
