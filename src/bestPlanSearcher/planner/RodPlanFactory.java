package bestPlanSearcher.planner;

import task.Blank;

public class RodPlanFactory {
    private final int rodLength;

    public RodPlanFactory(int rodLength) {
        this.rodLength = rodLength;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "rodLength: " + rodLength +
                "}";
    }

    public RodPlan createRodPlan(Blank blank) {
        return new RodPlan(rodLength, blank);
    }
}
