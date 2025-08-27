package bestPlanSearcher.permutationGenerator.utils;

public final class MathUtils {
    public static final int MAX_NUMBER_FOR_FACTORIAL = 12;

    public static int getFactorialOrMaxValue(int number) {
        return number > MAX_NUMBER_FOR_FACTORIAL ? Integer.MAX_VALUE : getFactorial(number);
    }

    private static int getFactorial(int number) {
        if (number <= 1) return 1;
        return number * getFactorial(number - 1);
    }
}
