package example.domain.model.schedule.construct;

import java.util.Objects;

/**
 * 候補日程の構成要素
 */
@ScheduleConstructValid
public class ScheduleConstruct {

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
        return Objects.hash(Objects.hashCode(meetingDate.hashCode()),
                Objects.hashCode(startTime.value),
                Objects.hashCode(endTime.value));
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
