package example.domain.model.meeting;

import java.util.Iterator;
import java.util.Set;

/**
 * 候補日程セット
 */
public class ProposedScheduleSet {
    Set<ProposedSchedule> set;

    ProposedScheduleSet(Set<ProposedSchedule> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public ProposedScheduleSet() {
    }

    public static ProposedScheduleSet of(Set<ProposedSchedule> source) {
        return new ProposedScheduleSet(source);
    }

    public Iterator<ProposedSchedule> list() {
        return set.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "ProposedScheduleSet{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
