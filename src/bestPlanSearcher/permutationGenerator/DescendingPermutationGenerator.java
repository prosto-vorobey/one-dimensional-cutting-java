package bestPlanSearcher.permutationGenerator;

import task.Blank;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DescendingPermutationGenerator implements PermutationGenerator {

    private final Set<Blank> blanks;

    public DescendingPermutationGenerator(Set<Blank> blanks) {
        this.blanks = Set.copyOf(blanks);
    }

    public List<Blank> getPermutation() {
        return blanks.stream()
                .sorted(Comparator.comparingInt(Blank::length).reversed())
                .collect(Collectors.toList());
    }
}
