package task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class TaskDataReader {
    private final Executor executor;

    public TaskDataReader(ExecutorService executor) {
        this.executor = Objects.requireNonNull(executor);
    }

    public List<TaskData> readAsync(List<String> filePaths) {
        var futures = filePaths.stream()
                .map(path -> CompletableFuture.supplyAsync(() -> readFile(path), executor))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    private TaskData readFile(String filePath) {
        try {
            return createTaskData(parseContent(Files.readString(Path.of(filePath))));
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

    private int[] parseContent(String content) {
        return Arrays.stream(content.trim().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private TaskData createTaskData(int[] data) {
        if (data.length < 1) {
            throw new IllegalArgumentException("Invalid data length: " + data.length);
        }

        var blankSet = new HashSet<Blank>();
        for (var i = 1; i < data.length; i++) {
            blankSet.add(new Blank(i - 1, data[i]));
        }

        return new TaskData(data[0], blankSet);
    }
}
