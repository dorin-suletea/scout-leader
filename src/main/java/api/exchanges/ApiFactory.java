package api.exchanges;

import api.exchanges.BittrexApi;


public interface ApiFactory {
    BittrexApi makeBittrexApi();
}
