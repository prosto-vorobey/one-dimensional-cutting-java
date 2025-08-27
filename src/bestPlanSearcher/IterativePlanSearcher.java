package bestPlanSearcher;

import bestPlanSearcher.permutationGenerator.PermutationSetGenerator;
import bestPlanSearcher.planner.Planner;

import java.util.Comparator;

public final class IterativePlanSearcher implements BestPlanSearcher {
    private final Planner planner;
    private final PermutationSetGenerator setGenerator;

    public IterativePlanSearcher(Planner planner, PermutationSetGenerator setGenerator) {
        this.planner = planner;
        this.setGenerator = setGenerator;
    }

    @Override
    public CuttingPlan findBest() {
        return setGenerator.getPermutationSet().parallelStream()
                .map(planner::generatePlan)
                .min(Comparator.comparing(CuttingPlan::getRodsAmount))
                .orElseThrow(() -> new IllegalStateException("Plan not found"));
    }

    @Override
    public String toString() {
        return "IterativePlanSearcher{" +
                "planner: " + planner + ", " +
                "setGenerator: " + setGenerator +
                "}";
    }
}
