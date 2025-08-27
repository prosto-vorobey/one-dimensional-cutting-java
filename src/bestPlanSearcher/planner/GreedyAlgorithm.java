package bestPlanSearcher.planner;

import bestPlanSearcher.CuttingPlan;
import task.Blank;

import java.util.ArrayList;
import java.util.List;

public final class GreedyAlgorithm implements Planner {
    private final RodPlanFactory rodPlanFactory;

    public GreedyAlgorithm(RodPlanFactory rodPlanFactory) {
        this.rodPlanFactory = rodPlanFactory;
    }

    @Override
    public CuttingPlan generatePlan(List<Blank> blanks) {
        var plan = new ArrayList<RodPlan>();

        for(var blank : blanks) {
            if (addBlankTo(plan, blank)) continue;
            plan.add(rodPlanFactory.createRodPlan(blank));
        }

        return new CuttingPlan(plan);
    }

    @Override
    public String toString() {
        return "GreedyAlgorithm{" +
                "rodPlanFactory: " + rodPlanFactory +
                "}";
    }

    private boolean addBlankTo(List<RodPlan> plan, Blank blank) {
        for(var rodPlan : plan) {
            if (rodPlan.addBlank(blank)) return true;
        }
        return false;
    }
}
