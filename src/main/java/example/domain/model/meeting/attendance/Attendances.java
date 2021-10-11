package example.domain.model.meeting.attendance;

import java.util.Iterator;
import java.util.Set;

/**
 * 出欠セット
 */
public class Attendances {
    Set<Attendance> set;

    Attendances(Set<Attendance> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public Attendances() {
    }

    public static Attendances of(Set<Attendance> source) {
        return new Attendances(source);
    }

    public Iterator<Attendance> list() {
        return set.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Attendances{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
