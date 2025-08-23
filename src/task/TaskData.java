package task;

import java.util.List;

public record TaskData(int rodLength, List<Blank> blanks) {

    public TaskData(int rodLength, List<Blank> blanks) {
        this.rodLength = rodLength;
        this.blanks = List.copyOf(blanks);
    }

    public int blankAmount() {
        return blanks.size();
    }
}
