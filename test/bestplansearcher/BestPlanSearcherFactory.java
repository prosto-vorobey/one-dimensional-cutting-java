package bestplansearcher;

import bestPlanSearcher.BasePlanSearcher;
import bestPlanSearcher.BestPlanSearcher;
import bestPlanSearcher.IterativePlanSearcher;
import bestPlanSearcher.permutationGenerator.PermutationGenerator;
import bestPlanSearcher.permutationGenerator.PermutationSetGenerator;
import bestPlanSearcher.planner.Planner;

public class BestPlanSearcherFactory {
    private final Planner planner;

    public BestPlanSearcherFactory(Planner planner) {
        this.planner = planner;
    }

    public BestPlanSearcher createBaseSearcher(PermutationGenerator generator) {
        return new BasePlanSearcher(planner, generator);
    }

    public BestPlanSearcher createIterativeSearcher(PermutationSetGenerator generator) {
        return new IterativePlanSearcher(planner, generator);
    }
}
