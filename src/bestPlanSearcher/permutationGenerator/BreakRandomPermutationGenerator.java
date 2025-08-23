package bestPlanSearcher.permutationGenerator;

import task.Blank;

import java.util.*;

public class BreakRandomPermutationGenerator implements PermutationGenerator {
    private final Set<Blank> blanks;
    private final int breakPosition;
    private final Random random;

    public BreakRandomPermutationGenerator(Set<Blank> blanks, double breakScale) {
        if (breakScale < 0) {
            throw new IllegalArgumentException("breakScale must be >= 0");
        }
        else if (breakScale > 1) {
            throw new IllegalArgumentException("breakScale must be <= 1");
        }
        this.blanks = Set.copyOf(blanks);
        breakPosition = (int) Math.floor(blanks.size() * breakScale);
        random = new Random();
    }

    @Override
    public List<Blank> getPermutation() {
        var result = new DescendingPermutationGenerator(blanks).getPermutation();
        sortPartAfterBreak(result);
        return result;
    }

    private void sortPartAfterBreak(List<Blank> permutation) {
        Collections.shuffle(permutation.subList(breakPosition, permutation.size()), random);
    }
}
