package example.domain.model.attendance;

import java.util.Iterator;
import java.util.Set;

/**
 * 出欠セット
 */
public class AttendanceSet {
    Set<Attendance> set;

    AttendanceSet(Set<Attendance> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public AttendanceSet() {
    }

    public static AttendanceSet of(Set<Attendance> source) {
        return new AttendanceSet(source);
    }

    public Iterator<Attendance> list() {
        return set.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "AttendanceSet{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
