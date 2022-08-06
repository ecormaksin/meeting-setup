package meeting.domain.model.attendance;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * 出欠リスト
 */
public class Attendances {

    List<@Valid Attendance> list;

    @Deprecated
    Attendances() {}

    public Attendances(final List<Attendance> list) {
        this.list = list;
    }

    public List<Attendance> asList() {
        return Collections.unmodifiableList(list);
    }
}
