package example.domain.model.schedule;

/**
 * 候補日程
 */
public class Schedule {
    ScheduleNumber scheduleNumber;
    MeetingDate meetingDate;
    StartTime startTime;
    EndTime endTime;

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
