package core;

/**
 * Created by next on 12/17/17.
 */
public class MathHelper {
    public static double percentOf(final double percent, final double number) {
        return (number * percent) / 100;
    }

    public static double percentGain(final double first, final double second) {
        return first / second * 100;
    }


}
