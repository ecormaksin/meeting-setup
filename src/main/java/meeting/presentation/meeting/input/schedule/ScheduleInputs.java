package meeting.presentation.meeting.input.schedule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public class ScheduleInputs {

    @NotNull
    List<@NotNull @Valid ScheduleInput> list;

    @Deprecated
    ScheduleInputs() {}

    public ScheduleInputs(@NotNull final List<@NotNull @Valid ScheduleInput> list) {
        this.list = list;
    }

    public List<ScheduleInput> asList() {
        return Collections.unmodifiableList(list);
    }
}
