package api;

import api.model.ApiPair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by next on 12/21/17.
 */
public class ApiDecoderProvider {

    List<ApiPair> decodeBittrexPairs(final String apiResponse){
        final List<ApiPair> ret = new ArrayList<>();
        try {
            JSONArray results = new JSONObject(apiResponse).getJSONArray("result");;
            System.out.println(results);
            for (int i=0;i<results.length();i++){
                JSONObject pairInfo = (JSONObject) results.get(i);

                String[] symbols = pairInfo.getString("MarketName").split("-");
                final String leftSymbol = symbols[0];
                final String rightSymbol = symbols[1];
                final double iBuyPriceNoFee = pairInfo.getDouble("Ask");
                final double iSellPriceNoFee = pairInfo.getDouble("Bid");

                ApiPair newPair = new ApiPair(leftSymbol, rightSymbol, iBuyPriceNoFee, iSellPriceNoFee);
                ret.add(newPair);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
