package api.exchanges;

import api.model.ApiInstrument;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ApiDecoderImpl implements ApiDecoder{

    public ApiDecoderImpl(){
    }

    public List<ApiInstrument> decodeBittrexPairs(final String apiResponse){
        final List<ApiInstrument> ret = new ArrayList<>();
        try {
            JSONArray results = new JSONObject(apiResponse).getJSONArray("result");;
            for (int i=0;i<results.length();i++){
                JSONObject pairInfo = (JSONObject) results.get(i);

                String[] symbols = pairInfo.getString("MarketName").split("-");
                final String leftSymbol = symbols[0];
                final String rightSymbol = symbols[1];
                final double iBuyPriceNoFee = pairInfo.getDouble("Ask");
                final double iSellPriceNoFee = pairInfo.getDouble("Bid");

                ApiInstrument newPair = new ApiInstrument(leftSymbol, rightSymbol, iBuyPriceNoFee, iSellPriceNoFee);
                ret.add(newPair);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
