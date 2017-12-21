package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by next on 12/21/17.
 */
public class ApiHelper {

    public static String getStreamContent(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseStr = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) !=null){
            responseStr.append(line);
        }
        return responseStr.toString();
    }

}
