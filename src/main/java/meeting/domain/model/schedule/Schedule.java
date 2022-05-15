package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.*;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

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

    @AssertTrue(message = "日程を追加する時は、未来の日時を指定してください。")
    boolean isFutureWhenAdd() {
        if (scheduleNumberIssued()) return true;
        return scheduleConstruct.isFuture();
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
