package planner;

import task.Blank;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlgorithm implements Planner {
    private final RodPlanFactory rodPlanFactory;
    private final List<Blank> blanks;

    public GreedyAlgorithm(RodPlanFactory rodPlanFactory, List<Blank> blanks) {
        this.rodPlanFactory = rodPlanFactory;
        this.blanks = new ArrayList<>(blanks);
    }

    @Override
    public CuttingPlan plan() {
        List <RodPlan> plan = new ArrayList<>();

        for(var blank : blanks) {
            if (addBlankTo(plan, blank)) continue;
            plan.add(rodPlanFactory.createRodPlan(blank));
        }

        return new CuttingPlan(plan);
    }

    private boolean addBlankTo(List<RodPlan> plan, Blank blank) {
        for(var rodPlan : plan) {
            if (rodPlan.addBlank(blank)) return true;
        }
        return false;
    }
}
