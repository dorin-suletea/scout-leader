package api;

import core.Config;
import core.model.Exchange;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeyProviderImpl implements KeyProvider {
    private final Map<Exchange, Pair<String, String>> keyMap;

    public KeyProviderImpl() {
        this.keyMap = getKeyMap();
    }

    @Override
    public String getApiKey(final Exchange exchange) {
        return keyMap.get(exchange).getKey();
    }

    @Override
    public String getApiSecret(final Exchange exchange) {
        return keyMap.get(exchange).getValue();
    }

    private Map<Exchange, Pair<String, String>> getKeyMap() {
        Map<Exchange, Pair<String, String>> ret = new HashMap<>();
        try (final BufferedReader br = new BufferedReader(new FileReader(Config.KEYSTORE_FILE_PATH))) {
            StringBuilder keystore = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                keystore.append(line);
            }
            JSONArray results = new JSONArray(keystore.toString());
            for (int i = 0; i < results.length(); i++) {
                JSONObject exchangeData = (JSONObject) results.get(i);
                final String exchangeName = exchangeData.getString("ex");
                final String key = exchangeData.getString("k");
                final String signature = exchangeData.getString("s");
                ret.put(Exchange.valueOf(exchangeName), new Pair<>(key, signature));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException("Bad keystore format at " + Config.KEYSTORE_FILE_PATH);
        }
        return ret;
    }
}
