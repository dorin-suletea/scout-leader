package api.exchanges.api;

import javafx.util.Pair;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by next on 12/21/17.
 */
public class ApiHelper {

    public static String sendGet(final String urlAddress) {
        return sendGet(urlAddress, Collections.EMPTY_LIST);
    }

    public static String sendGetWithSSLCert(final String requestUrl) {
        return sendGetWithSSLCert(requestUrl, Collections.EMPTY_LIST);
    }


    public static String sendGet(final String urlAddress, List<Pair<String, String>> headers) {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            for (Pair<String, String> header : headers) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            connection.connect();
            if (connection.getResponseCode() == 200) {
                return ApiHelper.getStreamContent(connection.getInputStream());
            } else {
                throw new RuntimeException("Request failed " + connection.getResponseCode() + " " + connection.getResponseMessage() + " " + ApiHelper.getStreamContent(connection.getErrorStream()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sendGetWithSSLCert(final String requestUrl, List<Pair<String, String>> headers) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            return sendGet(requestUrl, headers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeHmac(final String key, final String data) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getStreamContent(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseStr = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            responseStr.append(line);
        }
        return responseStr.toString();
    }
}
