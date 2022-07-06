package meeting.domain.model.schedule.construct;

import meeting.domain.model.meeting.RegistrationRequestDateTime;
import meeting.domain.model.schedule.EndDateTime;
import meeting.domain.model.schedule.StartDateTime;
import meeting.domain.type.time.TimeFormatter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 日程の構成要素
 */
public class ScheduleConstruct {

    @NotNull
    @Valid
    MeetingDate meetingDate;

    @NotNull
    @Valid
    StartHour startHour;

    @NotNull
    @Valid
    StartMinute startMinute;

    @NotNull
    @Valid
    Duration duration;

    @Deprecated
    ScheduleConstruct() {}

    public ScheduleConstruct(@NotNull @Valid final MeetingDate meetingDate,
                             @NotNull @Valid final StartHour startHour,
                             @NotNull @Valid final StartMinute startMinute,
                             @NotNull @Valid final Duration duration) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;
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

    public StartDateTime startDateTime() {
        return new StartDateTime(startLocalDateTime());
    }

    public EndDateTime endDateTime() {
        final LocalDateTime startLocalDateTime = startLocalDateTime();
        final LocalDateTime endLocalDateTime = startLocalDateTime.plusMinutes(duration.value());

        return new EndDateTime(endLocalDateTime);
    }

    public boolean isFutureThan(@NotNull final RegistrationRequestDateTime registrationRequestDateTime) {
        final LocalDateTime localDateTime = startLocalDateTime();

        return localDateTime.isAfter(registrationRequestDateTime.value());
    }

    private LocalDateTime startLocalDateTime() {
        final String timeString = String.format("%s:%s", startHour.formattedValue(), startMinute.label());
        final LocalTime localTime = LocalTime.parse(timeString, TimeFormatter.ofPattern());
        final LocalDateTime localDateTime = LocalDateTime.of(meetingDate.value(), localTime);

        return localDateTime;
    }

    public boolean isSame(@NotNull @Valid final ScheduleConstruct other) {
        if (!this.meetingDate.isSame(other.meetingDate)) return false;
        if (!this.startHour.isSame(other.startHour)) return false;
        if (this.startMinute != other.startMinute) return false;
        if (this.duration != other.duration) return false;

        return true;
    }

    @Deprecated
    @Override
    public final boolean equals(final Object anObject) {
        if (anObject == this) return true;
        if (!(anObject instanceof ScheduleConstruct other)) return false;
        return isSame(other);
    }

    @Deprecated
    @Override
    public final int hashCode() {
        return Objects.hash(meetingDate, startHour, startMinute, duration);
    }
}
