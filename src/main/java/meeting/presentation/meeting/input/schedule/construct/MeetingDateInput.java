package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.MeetingDate;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * 開催日（入力用）
 */
public class MeetingDateInput implements ScheduleConstructElement {

    String value;

    @Deprecated
    MeetingDateInput() {}

    public MeetingDateInput(String value) {
        this.value = value;
    }

    static MeetingDateInput empty() {
        return new MeetingDateInput("");
    }

    public static MeetingDateInput of(final String value) {
        return new MeetingDateInput(value);
    }

    public static MeetingDateInput of(final LocalDate localDate) {
        final String formatted = localDate.format(MeetingDate.FORMATTER);
        return of(formatted);
    }

    public static MeetingDateInput of(final MeetingDate meetingDate) {
        return new MeetingDateInput(meetingDate.stringValue());
    }

    @AssertTrue(message = MeetingDate.MESSAGE_VALID_FORMAT)
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    public boolean isEmpty() {
        if (Objects.isNull(value)) return true;
        return value.isEmpty();
    }

    public boolean isValidAsDomain() {
        if (isEmpty()) return false;
        try {
            toMeetingDate();
            return true;
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(MeetingDateInput other) {
        return this.value.equals(other.value);
    }

    public MeetingDate toMeetingDate() {
        return MeetingDate.of(value);
    }

    public String value() {
        return value;
    }
}
