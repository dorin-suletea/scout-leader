package api.exchanges.api;

import api.model.ApiAsset;
import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoloniexApiImpl implements PoloniexApi {


    @Override
    public List<ApiInstrument> getInstruments() {
        List<ApiInstrument> ret = new ArrayList<>();
        try {
            final String rawInstruments = ApiHelper.sendGet(PoloniexApi.INSTRUMENTS_URL);
            JSONObject rawData = new JSONObject(rawInstruments);
            for (Iterator<String> keyIterator = rawData.keys(); keyIterator.hasNext(); ) {
                String key = keyIterator.next();
                JSONObject rawItem = (JSONObject) rawData.get(key);
                String[] symbols = key.split("_");

                final String leftSymbol = symbols[0];
                final String rightSymbol = symbols[1];
                final Double iBuyPriceNoFee = new Double(rawItem.getString("lowestAsk"));
                final Double iSellPriceNoFee = new Double(rawItem.getString("highestBid"));

                ApiInstrument instrument = new ApiInstrument(leftSymbol, rightSymbol, iBuyPriceNoFee, iSellPriceNoFee);
                ret.add(instrument);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    @Override
    public List<ApiInstrumentInfo> getInstrumentsInfo() {
        List<ApiInstrumentInfo> ret = new ArrayList<>();
        try {
            final String ex = ApiHelper.sendGet(PoloniexApi.COIN_INFO_URL);
            JSONObject rawData = new JSONObject(ex);
            for (Iterator<String> keyIterator = rawData.keys(); keyIterator.hasNext(); ) {

                String coin = keyIterator.next();
                JSONObject rawItem = (JSONObject) rawData.get(coin);

                final double txFee = rawItem.getDouble("txFee");

                final boolean isFrozen = rawItem.getInt("frozen") != 0;
                final boolean isDisabled = rawItem.getInt("disabled") != 0;
                final boolean isDelisted = rawItem.getInt("delisted") != 0;
                final boolean validItem = !isFrozen && !isDisabled && !isDelisted;

                ApiInstrumentInfo instrumentInfo = new ApiInstrumentInfo(coin, txFee, validItem);
                ret.add(instrumentInfo);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    @Override
    public List<ApiAsset> getAssets() {
        throw new RuntimeException("Not implemented");
    }
}