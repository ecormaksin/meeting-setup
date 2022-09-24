package meeting.domain.model.attendance;

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

    public AttendancesResponseStatus responseStatus() {
        if (list.isEmpty()) return AttendancesResponseStatus.回答未発生;
        return AttendancesResponseStatus.回答発生済;
    }

    public List<Attendance> asList() {
        return Collections.unmodifiableList(list);
    }
}
