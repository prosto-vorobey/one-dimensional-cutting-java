import helper.TestDataHelper;
import org.junit.jupiter.api.*;
import task.TaskDataReader;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDataReaderTest {
    private static ExecutorService executor;
    private static List<String> filePaths;

    @BeforeAll
    public static void initFilePaths() {
        filePaths = TestDataHelper.findTxtFilesInResources();
        executor = TestDataHelper.createExecutor(filePaths.size(), 10);
    }

    @Test
    public void readAsync_shouldReturnCorrectNumberOfTaskData() throws ExecutionException, InterruptedException {
        var taskDataReader = new TaskDataReader(executor);
        var expectedResult = filePaths.size();

        var result = taskDataReader.readAsync(filePaths).get();

        assertEquals(expectedResult, result.size());
    }

    @AfterAll
    static public void tearDown() {
        executor.shutdown();
    }
}
