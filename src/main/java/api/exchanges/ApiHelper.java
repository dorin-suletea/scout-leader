package api.exchanges;

import api.model.ApiInstrument;
import api.model.ApiInstrumentInfo;
import core.model.Exchange;
import core.model.Instrument;
import core.model.InstrumentDirection;
import core.model.InstrumentInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by next on 12/21/17.
 */
public class ApiHelper {

    public static String sendGet(final String urlAddress) throws IOException {
        StringBuilder ret = new StringBuilder();
        BufferedReader rd = null;
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                ret.append(line);
            }
        } finally {
            if (rd != null) {
                rd.close();
            }
        }
        return ret.toString();
    }

    public static String getStreamContent(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseStr = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            responseStr.append(line);
        }
        return responseStr.toString();
    }

    public static InstrumentInfo unpackApiInstrumentInfo(final ApiInstrumentInfo apiInstrumentInfo,
                                                         final Exchange exchange) {
        return new InstrumentInfo(apiInstrumentInfo.getSymbol(), apiInstrumentInfo.getWithdrawalFee(), apiInstrumentInfo.isActive(), exchange);
    }

    public static List<Instrument> unpackApiInstrument(final ApiInstrument apiInstrument,
                                                       final double iBuyTradeFee,
                                                       final double iSellTradeFee,
                                                       final Exchange exchange) {
        final Double iBuyPriceNoFee = apiInstrument.getIBuyPriceNoFee();
        final Double iSellPriceNoFee = apiInstrument.getISellPriceNoFee();

        final Double iBuyPrice = iBuyPriceNoFee + iBuyTradeFee;
        final Double iSellPrice = iSellPriceNoFee - iSellTradeFee;

        final Instrument buyInstrument = new Instrument(
                apiInstrument.getLeftSymbol(),
                apiInstrument.getRightSymbol(),
                iBuyPrice,
                InstrumentDirection.BUY,
                exchange);

        final Instrument sellInstrument = new Instrument(
                apiInstrument.getRightSymbol(),
                apiInstrument.getLeftSymbol(),
                1 / iSellPrice,
                InstrumentDirection.SELL,
                exchange);


        List<Instrument> ret = new ArrayList<>();
        ret.add(buyInstrument);
        ret.add(sellInstrument);
        return ret;
    }

}
