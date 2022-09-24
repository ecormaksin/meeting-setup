package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.EndTime;
import meeting.domain.model.schedule.construct.MeetingDate;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.domain.model.schedule.construct.StartTime;

import javax.validation.Valid;
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

    public Schedule(ScheduleNumber scheduleNumber,
                    ScheduleConstruct scheduleConstruct) {
        this.scheduleNumber = scheduleNumber;
        this.scheduleConstruct = scheduleConstruct;
    }

    public static Schedule initializedBy(ScheduleConstruct scheduleConstruct) {
        return new Schedule(ScheduleNumber.initialized(),  scheduleConstruct);
    }

    public ScheduleRegistrationStatus registrationStatus() {
        return scheduleNumber.registrationStatus();
    }

    public boolean isDuplicated(@Valid Schedule other) {
        return this.scheduleConstruct.isSame(other.scheduleConstruct);
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

    public LocalDateTime startLocalDateTime() {
        return scheduleConstruct.startLocalDateTime();
    }
}
