package meeting.domain.model.schedule;

import meeting.domain.ValidationConcern;
import meeting.domain.model.meeting.RegistrationRequestDateTime;
import meeting.domain.model.schedule.construct.ScheduleConstruct;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 日程候補
 */
public class Schedules extends ValidationConcern {

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 5;

    public static final String SIZE_WITHIN_SPECIFIED_RANGE = "日程を"+ MIN_SIZE + "件以上" + MAX_SIZE + "件以下で指定してください。";
    public static final String SET_OF_UNIQUE_MEETING_DATE_TIMES = "日程は重複のないように指定してください。";

    @NotNull(message = SIZE_WITHIN_SPECIFIED_RANGE)
    @Size(min = MIN_SIZE, max = MAX_SIZE, message = SIZE_WITHIN_SPECIFIED_RANGE)
    List<@Valid Schedule> list;

    @Deprecated
    Schedules() {}

    public Schedules(final List<Schedule> list) {
        this.list = list;
        this.validate(this);
    }

    public static Schedules of(final List<Schedule> source) {
        return new Schedules(source);
    }

    public boolean isSetOfValidMeetingDateTimes(@Valid final RegistrationRequestDateTime registrationRequestDateTime) {
        return list.stream().allMatch(schedule -> schedule.isFutureInCaseOfAddition(registrationRequestDateTime));
    }

    @AssertTrue(message = SET_OF_UNIQUE_MEETING_DATE_TIMES)
    boolean isSetOfUniqueMeetingDateTimes() {

        Set<ScheduleConstruct> constructSet = new HashSet<>();
        for(Schedule schedule : list) {
            ScheduleConstruct construct = schedule.scheduleConstruct();
            boolean isAddedElement =  constructSet.add(construct);
            if (!isAddedElement) return false;
        }

        return true;
    }

    public List<Schedule> asList() {
        return Collections.unmodifiableList(list);
    }
}
