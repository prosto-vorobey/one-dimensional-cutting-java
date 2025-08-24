package bestPlanSearcher.permutationGenerator.utils;

public final class MathUtils {
    public static long getFactorial(int number) {
        if (number <= 1) return 1;
        return number * getFactorial(number - 1);
    }
}
