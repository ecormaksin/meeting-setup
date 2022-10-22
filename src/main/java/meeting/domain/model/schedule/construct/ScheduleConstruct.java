package meeting.domain.model.schedule.construct;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 日程の構成要素
 */
public class ScheduleConstruct {

    @Valid
    MeetingDate meetingDate;

    @Valid
    StartHour startHour;

    @Valid
    StartMinute startMinute;

    @Valid
    Duration duration;

    StartTime startTime;
    EndTime endTime;

    @Deprecated
    ScheduleConstruct() {}

    public ScheduleConstruct(MeetingDate meetingDate,
                             StartHour startHour,
                             StartMinute startMinute,
                             Duration duration) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;

        startTime = new StartTime(startLocalTime());
        endTime = new EndTime(endLocalTime());
    }

    public static ScheduleConstruct of(int year, int month, int dayOfMonth,
                                       int hour, StartMinute startMinute, Duration duration) {

        LocalDate meetingDateValue = LocalDate.of(year,month, dayOfMonth);

        return new ScheduleConstruct(
                MeetingDate.of(meetingDateValue),
                StartHour.of(hour),
                startMinute,
                duration);
    }

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

    public StartTime startTime() {
        return new StartTime(startLocalTime());
    }

    public EndTime endTime() {
        return new EndTime(endLocalTime());
    }

    private LocalTime startLocalTime() {
        return LocalTime.of(startHour.value(), startMinute.value());
    }

    private LocalTime endLocalTime() {
        return startLocalTime().plusMinutes(duration.value());
    }

    public LocalDateTime startLocalDateTime() {
        return LocalDateTime.of(meetingDate.value(), startLocalTime());
    }

    public boolean isSame(ScheduleConstruct other) {
        if (!this.meetingDate.isSame(other.meetingDate)) return false;
        if (!this.startHour.isSame(other.startHour)) return false;
        if (this.startMinute != other.startMinute) return false;
        if (this.duration != other.duration) return false;

        return true;
    }

    @Deprecated
    @Override
    public boolean equals(Object anObject) {
        if (anObject == this) return true;
        return isSame((ScheduleConstruct) anObject);
    }

    @Deprecated
    @Override
    public int hashCode() {
        return Objects.hash(meetingDate.value(),
                startHour.value(),
                startMinute.value(),
                duration.value());
    }
}
