package meeting.domain.model.schedule.construct;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 日程の構成要素
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

    public static ScheduleConstruct empty() {
        return new ScheduleConstruct(MeetingDate.empty(), StartTime.empty(), EndTime.empty());
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

    public ScheduleConstructValidity validity() {
        if (areAllEmpty()) return ScheduleConstructValidity.妥当_すべて未入力;
        if (areAnyEmpty()) return ScheduleConstructValidity.不当_一部未入力あり;
        if (isTimeRelationInvalid()) return ScheduleConstructValidity.不当_時刻の前後関係が不正;
        return ScheduleConstructValidity.妥当_すべて入力済;
    }

    private boolean areAllEmpty() {
        if (!meetingDate.isEmpty()) return false;
        if (!startTime.isEmpty()) return false;
        if (!endTime.isEmpty()) return false;
        return true;
    }

    private boolean areAnyEmpty() {
        if (meetingDate.isEmpty()) return true;
        if (startTime.isEmpty()) return true;
        if (endTime.isEmpty()) return true;
        return false;
    }

    private boolean isTimeRelationInvalid() {
        return startTime.value.compareTo(endTime.value) >= 0;
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof ScheduleConstruct other)) return false;
        if (!meetingDate.isEmpty()
                && !other.meetingDate.isEmpty()
                && !meetingDate.equals(other.meetingDate)) return false;
        if (!startTime.isEmpty()
                && !other.startTime.isEmpty()
                && !startTime.equals(other.startTime)) return false;
        if (!endTime.isEmpty()
                && !other.endTime.isEmpty()
                && !endTime.equals(other.endTime)) return false;
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
