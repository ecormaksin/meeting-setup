package example.domain.model.schedule;

import java.util.Objects;

/**
 * 候補日程
 */
@ScheduleValid
public class Schedule {
    ScheduleNumber scheduleNumber;
    MeetingDate meetingDate;
    StartTime startTime;
    EndTime endTime;

    public MeetingDate meetingDate() {
        return this.meetingDate;
    }

    public StartTime startTime() {
        return this.startTime;
    }

    public EndTime endTime() {
        return this.endTime;
    }

    public boolean areAllElementsNull() {
        return isMeetingDateNull() && isStartTimeNull() && isEndTimeNull();
    }

    public boolean areAllElementsNotNull() {
        return !isMeetingDateNull() && !isStartTimeNull() && !isEndTimeNull();
    }

    private boolean isMeetingDateNull() {
        return Objects.isNull(meetingDate) || meetingDate.isNull();
    }

    private boolean isStartTimeNull() {
        return Objects.isNull(startTime) || startTime.isNull();
    }

    private boolean isEndTimeNull() {
        return Objects.isNull(endTime) || endTime.isNull();
    }

    public boolean isStartTimeLessThanEndTime() {
        if (isStartTimeNull()) return false;
        if (isEndTimeNull()) return false;
        return startTime.value.compareTo(endTime.value) < 0;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Schedule{" +
                "scheduleNumber=" + scheduleNumber +
                ", meetingDate=" + meetingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
        // @formatter:on
    }
}
