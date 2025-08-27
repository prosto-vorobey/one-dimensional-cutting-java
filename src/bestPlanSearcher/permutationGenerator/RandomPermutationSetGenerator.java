package bestPlanSearcher.permutationGenerator;

import bestPlanSearcher.permutationGenerator.utils.MathUtils;
import task.Blank;

import java.util.*;

public final class RandomPermutationSetGenerator implements PermutationSetGenerator {
    private final int count;
    private final Set<Blank> blanks;
    private final Random random;

    public RandomPermutationSetGenerator(Set<Blank> blanks, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be > 1");
        }
        if (count > MathUtils.getFactorialOrMaxValue(blanks.size())) {
            throw new IllegalArgumentException("Count must be <= factorial of the set size");
        }

        this.blanks = Set.copyOf(blanks);
        this.count = count;
        this.random = new Random();
    }

    @Override
    public Set<List<Blank>> getPermutationSet() {
        var result = new HashSet<List<Blank>>();
        var counter = count;

        while (counter > 0) {
            if (result.add(generatePermutation())) {
                counter--;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "BreakRandomPermutationGenerator{" +
                "blanks: " + blanks + ", " +
                "count: " + count +
                "}";
    }

    private List<Blank> generatePermutation() {
        var result = new ArrayList<>(blanks);
        Collections.shuffle(result, random);

        return result;
    }
}
