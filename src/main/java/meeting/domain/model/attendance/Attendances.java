package meeting.domain.model.attendance;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * 出欠リスト
 */
public class Attendances {

    @NotNull
    List<@NotNull @Valid Attendance> list;

    @Deprecated
    Attendances() {}

    public Attendances(@NotNull final List<@NotNull @Valid Attendance> list) {
        this.list = list;
    }

    public List<Attendance> asList() {
        return Collections.unmodifiableList(list);
    }
}
