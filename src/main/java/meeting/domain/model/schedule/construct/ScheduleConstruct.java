package meeting.domain.model.schedule.construct;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 日程の構成要素
 */
@ScheduleConstructValid
public class ScheduleConstruct {

    @Valid
    MeetingDate meetingDate;

    @Valid
    StartHour startHour;

    @Valid
    StartMinute startMinute;

    @Valid
    Duration duration;

    public ScheduleConstruct(@Valid MeetingDate meetingDate,
                             @Valid StartHour startHour,
                             @Valid StartMinute startMinute,
                             @Valid Duration duration) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;
    }

    @Deprecated
    ScheduleConstruct() {}

    public MeetingDate meetingDate() {
        return meetingDate;
    }

    public StartHour startHour() {
        return startHour;
    }

    public StartMinute startMinute() {
        return startMinute;
    }

    public Duration duration() {
        return duration;
    }

    public ScheduleConstructValidity validity() {
        if (areAllEmpty()) return ScheduleConstructValidity.妥当_すべて未入力;
        if (areAnyEmpty()) return ScheduleConstructValidity.不当_一部未入力あり;
        if (isTimeRelationInvalid()) return ScheduleConstructValidity.不当_時刻の前後関係が不当;
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
