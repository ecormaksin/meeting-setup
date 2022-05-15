package meeting.domain.model.schedule;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * 日程候補
 */
public class Schedules {
    @NotNull
    List<@NotNull @Valid Schedule> list;

    @Deprecated
    Schedules() {}

    public Schedules(@NotNull final List<@NotNull @Valid Schedule> list) {
        this.list = list;
    }

    public static Schedules of(@NotNull final List<@NotNull @Valid Schedule> source) {
        return new Schedules(source);
    }

    @AssertTrue(message = "日程は重複のないように指定してください。")
    boolean isNotDuplicated() {
        return list.stream()
                .map(s -> s.scheduleConstruct)
                .distinct()
                .count() <= 1;
    }

    public List<Schedule> asList() {
        return Collections.unmodifiableList(list);
    }
}
