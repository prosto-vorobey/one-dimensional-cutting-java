package bestPlanSearcher.permutationGenerator;

import task.Blank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BreakRandomPermutationSetGenerator implements PermutationSetGenerator {
    private final Set<Blank> set;
    private final int stepCount;

    public BreakRandomPermutationSetGenerator(Set<Blank> set, int stepCount) {
        if (stepCount < 1) {
            throw new IllegalArgumentException("Steps count must be > 1");
        }

        this.set = set;
        this.stepCount = stepCount;
    }

    @Override
    public Set<List<Blank>> getPermutationSet() {
        var result = new HashSet<List<Blank>>();
        var counter = 0;

        while (counter < stepCount) {
            var breakScale = counter / (float)(stepCount - 1);
            if (result.add(generatePermutation(breakScale))) {
                counter++;
            }
        }

        return result;
    }

    private List<Blank> generatePermutation(float breakScale) {
        return new BreakRandomPermutationGenerator(set, breakScale).getPermutation();
    }
}
