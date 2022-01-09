package meeting.domain.model.schedule.construct;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 日程候補の構成要素
 */
@ScheduleConstructValid
public class ScheduleConstruct {

    MeetingDate meetingDate;
    StartTime startTime;
    EndTime endTime;

    public ScheduleConstruct(MeetingDate meetingDate, StartTime startTime, EndTime endTime) {
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static ScheduleConstruct of(LocalDate noWrappedMeetingDate,
                                       LocalTime noWrappedStartTime,
                                       LocalTime noWrappedEndTime) {
        return new ScheduleConstruct(
                new MeetingDate(noWrappedMeetingDate),
                new StartTime(noWrappedStartTime),
                new EndTime(noWrappedEndTime)
        );
    }

    @Deprecated
    ScheduleConstruct() {}

    public MeetingDate meetingDate() {
        return this.meetingDate;
    }

    public StartTime startTime() {
        return this.startTime;
    }

    public EndTime endTime() {
        return this.endTime;
    }

    public boolean isValid() {
        return isNotNull() && isStartTimeLessThanEndTime();
    }

    public boolean isNull() {
        return isMeetingDateNull() && isStartTimeNull() && isEndTimeNull();
    }

    public boolean isNotNull() {
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
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof ScheduleConstruct other)) return false;
        if (!other.isValid()) return false;
        if (!meetingDate.equals(other.meetingDate)) return false;
        if (!startTime.equals(other.startTime)) return false;
        if (!endTime.equals(other.endTime)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingDate, startTime, endTime);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "ScheduleConstruct{" +
                "meetingDate=" + meetingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
        // @formatter:on
    }
}
