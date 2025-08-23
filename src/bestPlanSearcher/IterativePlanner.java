package bestPlanSearcher;

import bestPlanSearcher.permutationGenerator.PermutationSetGenerator;
import bestPlanSearcher.planner.Planner;

import java.util.Optional;

public class IterativePlanner implements BestPlanSearcher {
    private final Planner algorithm;
    private final PermutationSetGenerator permutationSetGenerator;

    public IterativePlanner(Planner algorithm, PermutationSetGenerator permutationSetGenerator) {
        this.algorithm = algorithm;
        this.permutationSetGenerator = permutationSetGenerator;
    }

    public CuttingPlan searchBest() {
        Optional<CuttingPlan> bestPlan = Optional.empty();

        for(var permutation:permutationSetGenerator.getPermutationSet())
        {
            var plan = algorithm.planFor(permutation);
            if (bestPlan.isEmpty() || bestPlan.get().getRodsAmount() > plan.getRodsAmount()) {
                bestPlan = Optional.of(plan);
            }
        }

        return bestPlan.orElse(null);
    }
}
