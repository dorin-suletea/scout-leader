package core.transactions;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PermTest {

    public static void main(String[] args) {
        final int N = 6;

        int globalLineNumber = 1;

        for (int n = 3; n <= N; ++n) {
            int[] combination = IntStream.range(0, n).toArray();
            System.out.println("-----------");
            int lineNumber = 1;

            do {
                System.out.printf("[%3d] %3d: %s\n", globalLineNumber++, lineNumber++, Arrays.toString(combination));
            } while ((combination = generateNextCombination(combination, N)) != null);
        }
    }

    public static int[] generateNextCombination(int[] combination, int n) {
        if (combination[combination.length - 1] < n - 1) {
            combination[combination.length - 1]++;
            return combination;
        }

        for (int i = combination.length - 2; i >= 0; --i) {
            if (combination[i] < combination[i + 1] - 1) {
                combination[i]++;

                for (int j = i + 1; j < combination.length; ++j) {
                    combination[j] = combination[j - 1] + 1;
                }

                return combination;
            }
        }

        return null;
    }
}