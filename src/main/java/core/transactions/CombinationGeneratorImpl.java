package core.transactions;

import core.model.Instrument;

import java.util.ArrayList;
import java.util.List;

public class CombinationGeneratorImpl implements CombinationGenerator {

    public List<List<Instrument>> getCombinations(final int maxChainSize,
                                                  final List<Instrument> instrumentList,
                                                  final String baseCurrency) {
        List<List<Instrument>> permutations = new ArrayList<>();
        int chainSize = Math.min(maxChainSize, instrumentList.size());
        // Calculate the number of arrays we should create
        int numArrays = (int) Math.pow(instrumentList.size(), chainSize);
        for (int i = 0; i < numArrays; i++) {
            permutations.add(new ArrayList<>());
        }
        // Fill up the arrays
        for (int j = 0; j < chainSize; j++) {
            // This is the period with which this position changes, i.e.
            // a period of 5 means the value changes every 5th array
            int period = (int) Math.pow(instrumentList.size(), chainSize - j - 1);
            for (int i = 0; i < numArrays; i++) {
                List<Instrument> current = permutations.get(i);

                int index = i / period % instrumentList.size();
                Instrument target = instrumentList.get(index);
                if (!current.contains(target)) {
                    current.add(target);
                }
            }
        }
        //Return valid chains, cant filter up top since permutations are not comutative
        List<List<Instrument>> ret = new ArrayList<>();
        for (List<Instrument> permutation : permutations) {
            if (permutation.isEmpty()) {
                continue;
            }
            if (!permutation.get(0).getLeftSymbol().equals(baseCurrency)) {
                continue;
            }
            if (!permutation.get(permutation.size() - 1).getRightSymbol().equals(baseCurrency)) {
                continue;
            }

            boolean skip = false;
            for (int i = 0; i < permutation.size() - 1; i++) {
                if (!permutation.get(i).getRightSymbol().equals(permutation.get(i + 1).getLeftSymbol())) {
                    skip = true;
                    continue;
                }
            }
            if (skip) {
                continue;
            }
            ret.add(permutation);
        }
        return ret;
    }


    public static List<List<Integer>> getCombinations2(final List<Integer> num) {
        ArrayList<List<Integer>> result = new ArrayList<List<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());

        for (int i = 0; i < num.size(); i++) {
            //list of list in current iteration of the array num
            ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();

            for (List<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size() + 1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num.get(i);

                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);

                    //System.out.println(temp);

                    // - remove num[i] add
                    l.remove(j);
                }
            }

            result = new ArrayList<List<Integer>>(current);
        }

        return result;
    }

    public static void main(String[] args){
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        List<List<Integer>> out = getCombinations2()
    }
}
