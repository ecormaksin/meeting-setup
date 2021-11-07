package example.domain.model.schedule.construct;

import java.util.Objects;

/**
 * 候補日程の構成要素
 */
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
        if (!meetingDate.value.equals(other.meetingDate.value)) return false;
        if (!startTime.value.equals(other.startTime.value)) return false;
        if (!endTime.value.equals(other.endTime.value)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingDate, startTime, endTime);
    }
}
