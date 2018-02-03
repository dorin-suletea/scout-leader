package api.exchanges.api;

import api.model.ApiAsset;
import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BittrexApiImpl implements BittrexApi {

    @Override
    public List<ApiInstrument> getInstruments() {
        final List<ApiInstrument> ret = new ArrayList<>();
        try {
            String apiResponse = ApiHelper.sendGet(BittrexApi.INSTRUMENTS_URL);
            JSONArray results = new JSONObject(apiResponse).getJSONArray("result");
            for (int i = 0; i < results.length(); i++) {
                JSONObject instrumentData = (JSONObject) results.get(i);

                String[] symbols = instrumentData.getString("MarketName").split("-");
                final String leftSymbol = symbols[0];
                final String rightSymbol = symbols[1];
                final double iBuyPriceNoFee = instrumentData.getDouble("Ask");
                final double iSellPriceNoFee = instrumentData.getDouble("Bid");

                ApiInstrument newPair = new ApiInstrument(leftSymbol, rightSymbol, iBuyPriceNoFee, iSellPriceNoFee);
                ret.add(newPair);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<ApiInstrumentInfo> getInstrumentsInfo() {
        final List<ApiInstrumentInfo> ret = new ArrayList<>();
        try {
            String apiResponse = ApiHelper.sendGet(BittrexApi.COIN_INFO_URL);
            JSONArray results = new JSONObject(apiResponse).getJSONArray("result");
            for (int i = 0; i < results.length(); i++) {
                JSONObject instrumentInfo = (JSONObject) results.get(i);

                final String symbol = instrumentInfo.getString("Currency");
                final double withdrawalFee = instrumentInfo.getDouble("TxFee");
                final boolean enabled = instrumentInfo.getBoolean("IsActive");

                ApiInstrumentInfo info = new ApiInstrumentInfo(symbol, withdrawalFee, enabled);
                ret.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<ApiAsset> getAssets() {
        throw new RuntimeException("Not implemented");
    }
}
