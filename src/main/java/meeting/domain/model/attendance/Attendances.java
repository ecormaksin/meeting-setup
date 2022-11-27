package meeting.domain.model.attendance;

import meeting.domain.model.schedule.Schedule;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * 出欠リスト
 */
public class Attendances {

    List<@Valid Attendance> list;

    @Deprecated
    Attendances() {}

    public Attendances(List<Attendance> list) {
        this.list = list;
    }

    public static Attendances of(List<Attendance> list) {
        return new Attendances(list);
    }

    public static Attendances empty() {
        return new Attendances(Collections.emptyList());
    }

    public AttendancesResponseStatus responseStatus() {
        if (list.isEmpty()) return AttendancesResponseStatus.回答未発生;
        return AttendancesResponseStatus.回答発生済;
    }

    public List<Attendance> asList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Attendance attendance : list) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("""
                    {
                        "attendance": %s
                    }
                    """.formatted(attendance.toString()));
        }
        return "[%s]".formatted(sb.toString());
    }
}
