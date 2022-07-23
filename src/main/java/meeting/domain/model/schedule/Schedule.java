package meeting.domain.model.schedule;

import meeting.domain.model.meeting.RegistrationRequestDateTime;
import meeting.domain.model.schedule.construct.MeetingDate;
import meeting.domain.model.schedule.construct.ScheduleConstruct;

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

    public static Schedule initializedBy(final ScheduleConstruct scheduleConstruct) {
        return new Schedule(ScheduleNumber.initialized(),  scheduleConstruct);
    }

    public ScheduleRegistrationStatus registrationStatus() {
        return scheduleNumber.registrationStatus();
    }

    public boolean isDuplicated(@NotNull @Valid Schedule other) {
        return this.scheduleConstruct.isSame(other.scheduleConstruct);
    }

    @AssertTrue(message = "日程を追加する時は未来の日時を指定してください。")
    public boolean isFutureInCaseOfAddition(@NotNull final RegistrationRequestDateTime registrationRequestDateTime) {
        if (registrationStatus() == ScheduleRegistrationStatus.編集) return true;
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

    public StartTime startTime() {
        return scheduleConstruct.startTime();
    }

    public EndTime endTime() {
        return scheduleConstruct.endTime();
    }
}
