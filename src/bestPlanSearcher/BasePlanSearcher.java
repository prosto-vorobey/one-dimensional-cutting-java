package bestPlanSearcher;

import bestPlanSearcher.permutationGenerator.PermutationGenerator;
import bestPlanSearcher.planner.Planner;

public class BasePlanSearcher implements BestPlanSearcher {
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
}
