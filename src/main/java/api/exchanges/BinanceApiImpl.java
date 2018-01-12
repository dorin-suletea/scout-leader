package api.exchanges;

import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinanceApiImpl implements BinanceApi {


    @Override
    public List<ApiInstrument> getInstruments() {
        final List<ApiInstrument> ret = new ArrayList<>();
        try {
            String apiResponse = ApiHelper.sendGetWithSSLCert(BinanceApi.INSTRUMENTS_URL);

            JSONArray results = new JSONArray(apiResponse);
            for (int i = 0; i < results.length(); i++) {
                JSONObject instrumentData = (JSONObject) results.get(i);

                String symbols = instrumentData.getString("symbol");
                //on binance pairs are reversed, and we use the fact that all of the pairs you can buy for have 3 letters (ETH,BTC,BNB,USDT)
                final String rightSymbol = symbols.substring(0, symbols.length() - 3);
                final String leftSymbol = symbols.substring(symbols.length() - 3, symbols.length());
                final double iBuyPriceNoFee = instrumentData.getDouble("askPrice");
                final double iSellPriceNoFee = instrumentData.getDouble("bidPrice");

                ApiInstrument newPair = new ApiInstrument(leftSymbol, rightSymbol, iBuyPriceNoFee, iSellPriceNoFee);
                ret.add(newPair);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<ApiInstrumentInfo> getInstrumentsInfo() {
        List<ApiInstrumentInfo> ret = new ArrayList<>();
        for (Map.Entry<String, Double> instrumentFees : withdrawFeeMap().entrySet()) {
            ret.add(new ApiInstrumentInfo(
                    instrumentFees.getKey(),
                    instrumentFees.getValue(),
                    true));
        }

        return ret;
    }

    public Map<String, Double> withdrawFeeMap() {
        return new HashMap<String, Double>() {{
            put("BNB", 1d);
            put("BTC", 0.001);
            put("BTG", 0.001);
            put("ETH", 0.01);
            put("LTC", 0.01);
            put("NEO", 0d);
            put("QTUM", 0.01);
            put("SNT", 50d);
            put("BNT", 1.2);
            put("EOS", 0.7);
            put("BCC", 0.0005);
            put("GAS", 0d);
            put("USDT", 25d);
            put("OAX", 6d);
            put("DNT", 60d);
            put("MCO", 0.3);
            put("ICN", 2d);
            put("WTC", 0.4);
            put("OMG", 0.3);
            put("ZRX", 10d);
            put("STRAT", 0.1);
            put("SNGLS", 20d);
            put("BQX", 2d);
            put("KNC", 2d);
            put("FUN", 80d);
            put("SNM", 20d);
            put("LINK", 10d);
            put("XVG", 0.1);
            put("CTR", 7d);
            put("SALT", 0.4);
            put("IOTA", 0.5);
            put("MDA", 2d);
            put("MTL", 0.5);
            put("SUB", 4d);
            put("ETC", 0.01);
            put("MTH", 35d);
            put("ENG", 5d);
            put("AST", 10d);
            put("DASH", 0.002);
            put("EVX", 2.5);
            put("REQ", 15d);
            put("LRC", 12d);
            put("VIB", 20d);
            put("HSR", 0.0001);
            put("TRX", 100d);
            put("POWR", 8d);
            put("ARK", 0.1);
            put("YOYO", 10d);
            put("XRP", 0.15);
            put("MOD", 2d);
            put("ENJ", 80d);
            put("STORJ", 3d);
            put("VEN", 5d);
            put("KMD", 1d);
            put("NULS", 4d);
            put("RCN", 20d);
            put("RDN", 0.3);
            put("XMR", 0.04);
            put("DLT", 15d);
            put("AMB", 10d);
            put("BAT", 15d);
            put("ZEC", 0.005);
            put("BCPT", 14d);
            put("ARN", 7d);
            put("GVT", 0.5);
            put("CDT", 35d);
            put("GXS", 0.3);
            put("QSP", 30d);
            put("BTS", 1d);
            put("XZC", 0.02);
            put("LSK", 0.1);
            put("TNT", 35d);
            put("FUEL", 60d);
            put("MANA", 30d);
            put("BCD", 0.0005);
            put("DGD", 0.03);
            put("ADX", 2d);
            put("ADA", 1d);
            put("PPT", 0.01);
            put("CMT", 15d);
            put("XLM", 0.01);
            put("CND", 180d);
            put("LEND", 50d);
            put("WABI", 4d);
            put("TNB", 70d);
            put("WAVES", 0.002);
            put("ICX", 1.5);
            put("GTO", 30d);
            put("OST", 15d);
            put("ELF", 2d);
            put("AION", 1d);
            put("NEBL", 0.01);
            put("BRD", 3d);
            put("EDO", 1.5);
            put("WINGS", 3d);
            put("NAV", 0.2);
            put("LUN", 0.3);
            put("TRIG", 5d);
            put("RLC", 3d);
        }};
    }
}
