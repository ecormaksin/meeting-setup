package meeting.domain.model.participant.attendance;

import java.util.Collections;
import java.util.List;

/**
 * 出欠リスト
 */
public class Attendances {
    List<Attendance> list;

    Attendances(List<Attendance> list) {
        this.list = list;
    }

    @Deprecated
    Attendances() {
    }

    public static Attendances of(List<Attendance> source) {
        return new Attendances(source);
    }

    public List<Attendance> asList() {
        return Collections.unmodifiableList(list);
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
