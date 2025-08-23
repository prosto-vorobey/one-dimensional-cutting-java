package bestPlanSearcher;

import bestPlanSearcher.permutationGenerator.PermutationSetGenerator;
import bestPlanSearcher.planner.Planner;

import java.util.Optional;

public class IterativePlanSearcher implements BestPlanSearcher {
    private final Planner planner;
    private final PermutationSetGenerator permutationSetGenerator;

    public IterativePlanSearcher(Planner planner, PermutationSetGenerator permutationSetGenerator) {
        this.planner = planner;
        this.permutationSetGenerator = permutationSetGenerator;
    }

    public CuttingPlan searchBest() {
        Optional<CuttingPlan> bestPlan = Optional.empty();

        for(var permutation:permutationSetGenerator.getPermutationSet())
        {
            var plan = planner.planFor(permutation);
            if (bestPlan.isEmpty() || bestPlan.get().getRodsAmount() > plan.getRodsAmount()) {
                bestPlan = Optional.of(plan);
            }
        }

        return bestPlan.orElse(null);
    }
}
