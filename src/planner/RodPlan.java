package planner;

import task.Blank;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RodPlan{
    private final Set<Blank> blanks;
    private int remainingLength;

    public RodPlan(int length) {
        this.remainingLength = length;
        this.blanks =  new HashSet<>();
    }

    public RodPlan(int length, Blank blank) {
        this(length);
        this.blanks.add(blank);
    }

    public int getRemainingLength() {
        return remainingLength;
    }

    public Set<Blank> getBlanks() {
        return Collections.unmodifiableSet(blanks);
    }

    public boolean addBlank(Blank blank) {
        if (blank.length() > remainingLength || blanks.contains(blank)) return false;
        blanks.add(blank);
        remainingLength -= blank.length();
        return true;
    }
}
