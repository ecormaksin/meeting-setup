package meeting.domain.model.schedule;

import meeting.domain.model.meeting.RegistrationRequestDateTime;
import meeting.domain.model.schedule.construct.MeetingDate;
import meeting.domain.model.schedule.construct.ScheduleConstruct;

import javax.validation.Valid;
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

    public boolean isFutureThanWhenAdd(@NotNull final RegistrationRequestDateTime registrationRequestDateTime) {
        if (scheduleNumberIssued()) return true;
        return scheduleConstruct.isFutureThan(registrationRequestDateTime);
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

    public String startHourStringValue() {
        return scheduleConstruct.startHour().stringValue();
    }

    public String startMinuteLabel() {
        return scheduleConstruct.startMinute().label();
    }

    public String durationStringValue() {
        return scheduleConstruct.duration().stringValue();
    }
}
