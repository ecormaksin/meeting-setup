package example.domain.model.schedule;

import example.domain.model.schedule.construct.EndTime;
import example.domain.model.schedule.construct.MeetingDate;
import example.domain.model.schedule.construct.ScheduleConstruct;
import example.domain.model.schedule.construct.StartTime;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * 候補日程
 */
public class Schedule {
    ScheduleNumber scheduleNumber;

    @Valid
    ScheduleConstruct scheduleConstruct;

    public ScheduleConstruct scheduleConstruct() {
        return scheduleConstruct;
    }

    public boolean isValid() {
        return scheduleConstruct.isValid();
    }

    public boolean isConstructNotNull() {
        return scheduleConstruct.isNotNull();
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

    public LocalDateTime startDateTime() {
        return LocalDateTime.of(meetingDate().value(), startTime().value());
    }

    public LocalDateTime endDateTime() {
        return LocalDateTime.of(meetingDate().value(), endTime().value());
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Schedule{" +
                "scheduleNumber=" + scheduleNumber +
                ", scheduleConstruct=" + scheduleConstruct +
                '}';
        // @formatter:on
    }
}
