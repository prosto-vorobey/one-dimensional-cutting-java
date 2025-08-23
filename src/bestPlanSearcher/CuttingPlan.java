package bestPlanSearcher;

import bestPlanSearcher.planner.RodPlan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CuttingPlan {

    private final List<RodPlan> plan;

    public CuttingPlan(List<RodPlan> plan) {
        this.plan = new ArrayList<>(plan);
    }

    public int getRodsAmount( ){
        return plan.size();
    }

    public List<RodPlan> getRods() {
        return Collections.unmodifiableList(plan);
    }
}
