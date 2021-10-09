package example.domain.model.meeting;

import java.util.Set;

/**
 * 候補日程のセット
 */
public class ProposedSchedules {
    Set<ProposedSchedule> set;

    ProposedSchedules(Set<ProposedSchedule> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public ProposedSchedules() {
    }

    public static ProposedSchedules of(Set<ProposedSchedule> set) {
        return new ProposedSchedules(set);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "ProposedSchedules{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
