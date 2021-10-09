package example.domain.model.meeting.reply;

import java.util.Iterator;
import java.util.Set;

/**
 * 複数の候補日程への回答
 */
public class RepliesToProposedSchedules {
    Set<ReplyToProposedSchedule> set;

    RepliesToProposedSchedules(Set<ReplyToProposedSchedule> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public RepliesToProposedSchedules() {
    }

    public static RepliesToProposedSchedules of(Set<ReplyToProposedSchedule> source) {
        return new RepliesToProposedSchedules(source);
    }

    public Iterator<ReplyToProposedSchedule> list() {
        return set.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "RepliesToProposedSchedules{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
