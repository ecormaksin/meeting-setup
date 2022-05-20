package meeting.presentation.meeting.input.schedule;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleInputs {

    @NotNull
    List<@NotNull @Valid ScheduleInput> list;

    @Deprecated
    ScheduleInputs() {}

    public ScheduleInputs(@NotNull final List<@NotNull @Valid ScheduleInput> list) {
        this.list = list;
    }

    @AssertTrue(message = "日程を1件以上指定してください。")
    boolean haveAtLeastOne() {
        return list.stream().anyMatch(s -> s.isValidAsDomain());
    }

    @AssertTrue(message = "日程は重複のないように指定してください。")
    boolean areNotDuplicated() {
        final List<ScheduleInput> validInputs = list.stream()
                .filter(s -> s.isValidAsDomain())
                .collect(Collectors.toList());
        if (validInputs.isEmpty()) return true;

        for (int i = 0; i < validInputs.size()-1; i++) {
            final ScheduleInput outer = validInputs.get(i);
            for (int j = i + 1; j < validInputs.size(); j++) {
                final ScheduleInput inner = validInputs.get(j);
                if (inner.isDuplicated(outer)) return false;
            }
        }
        return true;
    }

    public List<ScheduleInput> asList() {
        return Collections.unmodifiableList(list);
    }
}
