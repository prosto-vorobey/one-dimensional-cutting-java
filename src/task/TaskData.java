package task;

import java.util.Set;

public record TaskData(int rodLength, Set<Blank> blanks) {
    public TaskData {
        blanks = Set.copyOf(blanks);
    }

    public int blankAmount() {
        return blanks.size();
    }
}
