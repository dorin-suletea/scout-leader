package core;

import java.text.DecimalFormat;

/**
 * Created by next on 12/17/17.
 */
public class StringHelper {
    public static String formattedDouble(final Double input){
        DecimalFormat decimalFormat = new DecimalFormat("#.####################");
        return decimalFormat.format(input);
    }
}
