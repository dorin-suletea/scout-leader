package api;

import core.model.Exchange;

public interface KeyProvider {
    String getApiKey(Exchange exchange);
    String getApiSecret(Exchange exchange);
}
