package meeting.domain.model.schedule;

import meeting.domain.ValidationConcern;
import meeting.domain.model.meeting.RegistrationRequestDateTime;

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

    public static final String MESSAGE_HAS_AT_LEAST_ONE_SCHEDULE = "日程を1件以上指定してください。";
    public static final String MESSAGE_HAS_UNIQUE_START_DATE_TIMES = "日程は重複のないように指定してください。";

    @NotNull(message = MESSAGE_HAS_AT_LEAST_ONE_SCHEDULE)
    @Size(min = 1, message = MESSAGE_HAS_AT_LEAST_ONE_SCHEDULE)
    List<@NotNull @Valid Schedule> list;

    @Deprecated
    Schedules() {}

    public Schedules(final List<Schedule> list) {
        this.list = list;
        this.validate(this);
    }

    public static Schedules of(final List<Schedule> source) {
        return new Schedules(source);
    }

    public boolean isSetOfValidStartDateTime(@NotNull @Valid final RegistrationRequestDateTime registrationRequestDateTime) {
        return list.stream().allMatch(schedule -> schedule.isFutureInCaseOfAddition(registrationRequestDateTime));
    }

    @AssertTrue(message = MESSAGE_HAS_UNIQUE_START_DATE_TIMES)
    boolean isSetOfUniqueStartDateTime() {
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
