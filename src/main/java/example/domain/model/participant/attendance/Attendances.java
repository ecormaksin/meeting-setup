package example.domain.model.participant.attendance;

import java.util.Iterator;
import java.util.List;

/**
 * 出欠リスト
 */
public class Attendances {
    List<Attendance> list;

    Attendances(List<Attendance> list) {
        this.list = list;
    }

    @Deprecated(since = "MyBatis")
    public Attendances() {
    }

    public static Attendances of(List<Attendance> source) {
        return new Attendances(source);
    }

    public Iterator<Attendance> list() {
        return list.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Attendances{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
