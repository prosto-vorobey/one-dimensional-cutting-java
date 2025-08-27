import bestPlanSearcher.BestPlanSearcher;
import bestPlanSearcher.permutationGenerator.BreakRandomPermutationGenerator;
import bestPlanSearcher.permutationGenerator.BreakRandomPermutationSetGenerator;
import bestPlanSearcher.permutationGenerator.DescendingPermutationGenerator;
import bestPlanSearcher.permutationGenerator.RandomPermutationSetGenerator;
import bestPlanSearcher.planner.GreedyAlgorithm;
import bestPlanSearcher.planner.RodPlanFactory;
import bestplansearcher.BestPlanSearcherFactory;
import helper.TestDataHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BestPlanSearchersDeviationScaleTest {
    private static final float POSSIBLE_DEVIATION_SCALE = 0.1f;
    private static final float BREAK_SCALE = 0.5f;
    private static final List<Integer> OPTIMAL_RESULTS = List.of(4, 4, 28, 28, 28, 341, 346, 341, 342, 341);
    private static Map<String, List<TestData>> searchersMap;

    @BeforeAll
    public static void init()
    {
        var taskDataList = TestDataHelper.readTaskDataFromResources();
        if (OPTIMAL_RESULTS.size() != taskDataList.size()) {
            throw new IllegalStateException("Results list size doesn't match task data size");
        }

        searchersMap = new LinkedHashMap<>(Map.of(
                "BaseSearcherWithDescending", new ArrayList<>(),
                "BaseSearcherWithBreakRandom", new ArrayList<>(),
                "IterativeSearcherWithRandomSet", new ArrayList<>(),
                "IterativeSearcherWithBreakRandomSet", new ArrayList<>()
        ));

        for(var i = 0; i < taskDataList.size(); i++) {
            var taskData = taskDataList.get(i);
            var optimum = OPTIMAL_RESULTS.get(i);
            var searcherFactory = createSearcherFactory(taskData.rodLength());
            searchersMap.get("BaseSearcherWithDescending").add(new TestData(
                    searcherFactory.createBaseSearcher(new DescendingPermutationGenerator(taskData.blanks())),
                    optimum
            ));
            searchersMap.get("BaseSearcherWithBreakRandom").add(new TestData(
                    searcherFactory.createBaseSearcher(new BreakRandomPermutationGenerator(taskData.blanks(),
                            BREAK_SCALE)),
                    optimum
            ));
            searchersMap.get("IterativeSearcherWithRandomSet").add(new TestData(
                    searcherFactory.createIterativeSearcher(new RandomPermutationSetGenerator(taskData.blanks(),
                            (int) Math.pow(taskData.blanks().size(), 2))),
                    optimum
            ));
            searchersMap.get("IterativeSearcherWithBreakRandomSet").add(new TestData(
                    searcherFactory.createIterativeSearcher(new BreakRandomPermutationSetGenerator(taskData.blanks(),
                            taskData.blanks().size())),
                    optimum
            ));
        }
    }

    @ParameterizedTest
    @MethodSource("provideSearcherTypes")
    public void bestPlanSearcher_shouldHavePossibleDeviation(String searcherType, TestReporter testReporter) {
        var testDataList = searchersMap.get(searcherType);
        var averageDeviation = testDataList.stream()
                .mapToDouble(TestData::calculateBestPlanRodsAmountDeviation)
                .average();

        if (averageDeviation.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot calculate average deviation: test data list is empty for searcher type " + searcherType
            );
        }

        var result = averageDeviation.getAsDouble();

        assertTrue(result <= POSSIBLE_DEVIATION_SCALE);

        testReporter.publishEntry(searcherType, String.valueOf(result));
    }

    private static Stream<Arguments> provideSearcherTypes() {
        return Stream.of(
                Arguments.of("BaseSearcherWithDescending"),
                Arguments.of("BaseSearcherWithBreakRandom"),
                Arguments.of("IterativeSearcherWithRandomSet"),
                Arguments.of("IterativeSearcherWithBreakRandomSet")
        );
    }

    private static BestPlanSearcherFactory createSearcherFactory(int rodLength) {
        return new BestPlanSearcherFactory(new GreedyAlgorithm(new RodPlanFactory(rodLength)));
    }

    private record TestData(BestPlanSearcher searcher, int optimum) {
        public double calculateBestPlanRodsAmountDeviation() {
            var rodsAmount = searcher.findBest().getRodsAmount();
            return (rodsAmount - optimum) / (double)optimum;
        }
    }
}
