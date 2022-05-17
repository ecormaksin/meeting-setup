package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.MeetingDate;
import meeting.domain.model.schedule.construct.ScheduleConstruct;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 日程
 */
public class Schedule {
    @Valid
    ScheduleNumber scheduleNumber;

    @Valid
    ScheduleConstruct scheduleConstruct;

    @Deprecated
    Schedule() {}

    public Schedule(final ScheduleNumber scheduleNumber,
                    final ScheduleConstruct scheduleConstruct) {
        this.scheduleNumber = scheduleNumber;
        this.scheduleConstruct = scheduleConstruct;
    }

    public boolean scheduleNumberIssued() {
        return scheduleNumber.issued();
    }

    public boolean isDuplicated(@NotNull @Valid Schedule other) {
        return this.scheduleConstruct.isSame(other.scheduleConstruct);
    }

    public boolean isFutureWhenAdd(@NotNull final LocalDateTime dateTimeToCompare) {
        if (scheduleNumberIssued()) return true;
        return scheduleConstruct.isFuture(dateTimeToCompare);
    }

    public ScheduleNumber scheduleNumber() {
        return scheduleNumber;
    }

    public ScheduleConstruct scheduleConstruct() {
        return scheduleConstruct;
    }

    public MeetingDate meetingDate() {
        return scheduleConstruct.meetingDate();
    }

    public int startHourValue() {
        return scheduleConstruct.startHour().value();
    }

    public String startMinuteLabel() {
        return scheduleConstruct.startMinute().label();
    }

    public int durationValue() {
        return scheduleConstruct.duration().value();
    }
}
