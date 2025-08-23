package bestPlanSearcher.planner;

import bestPlanSearcher.CuttingPlan;
import task.Blank;

import java.util.List;

public interface Planner {
    CuttingPlan planFor(List<Blank> blanks);
}
