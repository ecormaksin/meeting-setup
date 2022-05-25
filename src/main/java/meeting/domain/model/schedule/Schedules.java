package meeting.domain.model.schedule;

import meeting.domain.ValidationConcern;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * 日程候補
 */
public class Schedules extends ValidationConcern {
    @NotNull
    @Size(min = 1, message = "日程を1件以上指定してください。")
    List<@NotNull @Valid Schedule> list;

    @Deprecated
    Schedules() {}

    public Schedules(@NotNull final List<@NotNull @Valid Schedule> list) {
        this.list = list;
        this.validate(this);
    }

    public static Schedules of(@NotNull final List<@NotNull @Valid Schedule> source) {
        return new Schedules(source);
    }

    @AssertTrue(message = "日程は重複のないように指定してください。")
    boolean areNotDuplicated() {
        for (int i = 0; i < list.size()-1; i++) {
            final Schedule outer = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                final Schedule inner = list.get(j);
                if (inner.isDuplicated(outer)) return false;
            }
        }
        return true;
    }

    public List<Schedule> asList() {
        return Collections.unmodifiableList(list);
    }
}
