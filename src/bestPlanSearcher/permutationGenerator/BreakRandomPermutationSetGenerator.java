package bestPlanSearcher.permutationGenerator;

import task.Blank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class BreakRandomPermutationSetGenerator implements PermutationSetGenerator {
    private final Set<Blank> blanks;
    private final int count;

    public BreakRandomPermutationSetGenerator(Set<Blank> blanks, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Steps count must be > 1");
        }
        if (count > blanks.size()) {
            throw new IllegalArgumentException("Steps count must be <= set size");
        }

        this.blanks = blanks;
        this.count = count;
    }

    @Override
    public Set<List<Blank>> getPermutationSet() {
        var result = new HashSet<List<Blank>>();
        var counter = count - 1;

        while (counter >= 0) {
            var breakScale = counter / (float)(count);
            if (result.add(generatePermutation(breakScale))) {
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

    private List<Blank> generatePermutation(float breakScale) {
        return new BreakRandomPermutationGenerator(blanks, breakScale).getPermutation();
    }
}
