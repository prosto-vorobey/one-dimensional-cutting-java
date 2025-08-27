package bestPlanSearcher;

import bestPlanSearcher.permutationGenerator.PermutationGenerator;
import bestPlanSearcher.planner.Planner;

public final class BasePlanSearcher implements BestPlanSearcher {
    private final Planner planner;
    private final PermutationGenerator generator;

    public BasePlanSearcher(Planner planner, PermutationGenerator generator) {
        this.planner = planner;
        this.generator = generator;
    }

    @Override
    public CuttingPlan findBest() {
        return planner.generatePlan(generator.getPermutation());
    }

    @Override
    public String toString() {
        return "BasePlanSearcher{" +
                "planner: " + planner + ", " +
                "generator: " + generator +
                "}";
    }
}
