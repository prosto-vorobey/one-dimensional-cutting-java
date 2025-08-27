package bestPlanSearcher;

import bestPlanSearcher.permutationGenerator.PermutationSetGenerator;
import bestPlanSearcher.planner.Planner;

import java.util.Comparator;

public class IterativePlanSearcher implements BestPlanSearcher {
    private final Planner planner;
    private final PermutationSetGenerator permutationSetGenerator;

    public IterativePlanSearcher(Planner planner, PermutationSetGenerator permutationSetGenerator) {
        this.planner = planner;
        this.permutationSetGenerator = permutationSetGenerator;
    }

    public CuttingPlan findBest() {
        return permutationSetGenerator.getPermutationSet().parallelStream()
                .map(planner::generatePlan)
                .min(Comparator.comparing(CuttingPlan::getRodsAmount))
                .orElseThrow(() -> new IllegalStateException("Plan not found"));
    }
}
