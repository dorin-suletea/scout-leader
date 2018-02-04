package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static JSONArray getJsonArray(final String str) {
        try {
            return new JSONArray(str);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getJsonObject(final String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getFromArray(final JSONArray array,final int index) {
        try {
            return array.getJSONObject(index);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getString(final JSONObject object,final String tag) {
        try {
            return object.getString(tag);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getDouble(final JSONObject object,final String tag) {
        try {
            return object.getDouble(tag);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
