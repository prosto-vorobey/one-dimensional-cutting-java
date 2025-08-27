import bestPlanSearcher.BestPlanSearcher;
import bestPlanSearcher.permutationGenerator.*;
import bestPlanSearcher.planner.GreedyAlgorithm;
import bestPlanSearcher.planner.RodPlanFactory;
import bestplansearcher.BestPlanSearcherFactory;
import helper.TestDataHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import task.TaskData;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BestPlanSearchersTest {
    private static final List<Integer> GREEDY_DESCENDING_RESULTS
            = List.of(5, 5, 30, 30, 30, 352, 357, 353, 354, 352);
    private static final List<Integer> OPTIMAL_RESULTS = List.of(4, 4, 28, 28, 28, 341, 346, 341, 342, 341);
    private static final float BREAK_SCALE = 0.5f;
    private static Map<TaskData, Integer> greedyDescendingMap;
    private static Map<TaskData, Integer> optimumMap;

    @BeforeAll
    public static void init()
    {
        var taskDataList = TestDataHelper.readTaskDataFromResources();
        if (OPTIMAL_RESULTS.size() != taskDataList.size() || GREEDY_DESCENDING_RESULTS.size() != taskDataList.size() ) {
            throw new IllegalStateException("Results list size doesn't match task data size");
        }

        optimumMap = new LinkedHashMap<>();
        greedyDescendingMap = new LinkedHashMap<>();

        for(var i = 0; i < taskDataList.size(); i++) {
            optimumMap.put(taskDataList.get(i), OPTIMAL_RESULTS.get(i));
            greedyDescendingMap.put(taskDataList.get(i), GREEDY_DESCENDING_RESULTS.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("provideGreedyDescendingTestData")
    public void greedyWithDescendingPermutation_shouldReturnExpectedRodsAmount(TaskData taskData, int expectedResult) {
        var greedyAlgorithm = createGreedyAlgorithm(taskData.rodLength());
        var permutationGenerator = new DescendingPermutationGenerator(taskData.blanks());

        var result = greedyAlgorithm.generatePlan(permutationGenerator.getPermutation());

        assertEquals(expectedResult, result.getRodsAmount());
    }

    @ParameterizedTest
    @MethodSource("provideBestPlanSearcherTestData")
    public void bestPlanSearcher_shouldNotLessOptimalRodsAmount(BestPlanSearcher searcher, int optimalResult, TestReporter testReporter) {
        var result = searcher.findBest();

        assertTrue(optimalResult <= result.getRodsAmount());

        testReporter.publishEntry("BestPlanSearcher", String.valueOf(searcher));
        testReporter.publishEntry("Expected", String.valueOf(optimalResult));
        testReporter.publishEntry("Actual", String.valueOf(result.getRodsAmount()));
    }

    private static Stream<Arguments> provideGreedyDescendingTestData() {
        return greedyDescendingMap.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> provideBestPlanSearcherTestData() {
        return optimumMap.entrySet().stream()
                .flatMap(entry -> createSearchers(entry.getKey()).stream()
                        .map(searcher -> Arguments.of(searcher, entry.getValue())));
    }

    private static List<BestPlanSearcher> createSearchers(TaskData taskData) {
        if (taskData.blanks().isEmpty()) {
            return Collections.emptyList();
        }

        List<BestPlanSearcher> result = new ArrayList<>();
        var planner = createGreedyAlgorithm(taskData.rodLength());
        var searcherFactory = new BestPlanSearcherFactory(planner);
        result.add(searcherFactory.createBaseSearcher(new BreakRandomPermutationGenerator(taskData.blanks(), BREAK_SCALE)));
        result.add(searcherFactory.createIterativeSearcher(new RandomPermutationSetGenerator(taskData.blanks(),
                (int) Math.pow(taskData.blanks().size(), 2))));
        result.add(searcherFactory.createIterativeSearcher(new BreakRandomPermutationSetGenerator(taskData.blanks(),
                taskData.blanks().size())));

        return result;
    }

    private static GreedyAlgorithm createGreedyAlgorithm(int rodLength) {
        return new GreedyAlgorithm(new RodPlanFactory(rodLength));
    }
}
