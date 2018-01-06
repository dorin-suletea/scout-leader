package api.exchanges;

import api.model.ApiInstrument;
import api.model.InstrumentWitdrawalFee;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BittrexApiImpl implements BittrexApi {

    @Override
    public List<ApiInstrument> getInstruments() {
        final List<ApiInstrument> ret = new ArrayList<>();
        try {

            //Method method = this.getClass().getInterfaces()[0].getDeclaredMethod("getInstruments");
//            Annotation[] an1 = new Object() {}.getClass().getMethod("getInstruments").getAnnotations();
            Method method = new Object() {}.getClass().getEnclosingMethod();
            Annotation[][] an = method.getParameterAnnotations();

            String requestLine = this.getClass().getInterfaces()[0].getDeclaredMethod("getInstruments").getAnnotation(RequestLine.class).requestLine();
            String apiResponse = ApiHelper.sendGet(requestLine);
            JSONArray results = new JSONObject(apiResponse).getJSONArray("result");
            for (int i = 0; i < results.length(); i++) {
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<InstrumentWitdrawalFee> getWitdrawalFees() {
        return null;
    }
}
