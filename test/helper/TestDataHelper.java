package helper;

import task.TaskData;
import task.TaskDataReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public final class TestDataHelper {
    private TestDataHelper() {}

    public static List<TaskData> readTaskData() {
        try {
            return new TaskDataReader(Executors.newCachedThreadPool()).readAsync(findTxtFilesInResources()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> findTxtFilesInResources() {
        try {
            var resourcesUrl = Objects.requireNonNull(TestDataHelper.class
                    .getClassLoader()
                    .getResource(""));
            var resourcesPath = Paths.get(resourcesUrl.toURI());
            var matcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");

            try (Stream<Path> walkStream = Files.walk(resourcesPath)) {
                return walkStream
                        .filter(Files::isRegularFile)
                        .filter(path -> matcher.matches(path.getFileName()))
                        .map(Path::toString)
                        .toList();
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Error accessing resources", e);
        }
    }
}
