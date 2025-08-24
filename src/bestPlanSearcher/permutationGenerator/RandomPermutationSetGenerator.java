package bestPlanSearcher.permutationGenerator;

import bestPlanSearcher.permutationGenerator.utils.MathUtils;
import task.Blank;

import java.util.*;

public class RandomPermutationSetGenerator implements PermutationSetGenerator {
    private final int count;
    private final Set<Blank> set;
    private final Random random;

    public RandomPermutationSetGenerator(Set<Blank> set, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be > 1");
        }
        if (count > MathUtils.getFactorial(set.size())) {
            throw new IllegalArgumentException("Count must be <= factorial of the set size");
        }

        this.set = Set.copyOf(set);
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

    private List<Blank> generatePermutation() {
        var result = new ArrayList<>(set);
        Collections.shuffle(result, random);

        return result;
    }
}
