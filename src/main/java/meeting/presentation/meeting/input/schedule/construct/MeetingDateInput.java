package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.MeetingDate;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * 開催日（入力用）
 */
public class MeetingDateInput {

    String value;

    boolean valid;

    @Deprecated
    MeetingDateInput() {}

    public MeetingDateInput(String value) {
        this.value = value;

        valid = isValid();
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

    public static MeetingDateInput of(final int year, final int month, final int dayOfMonth) {
        final LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        return of(localDate);
    }

    public static MeetingDateInput of(final MeetingDate meetingDate) {
        return new MeetingDateInput(meetingDate.stringValue());
    }

    @AssertTrue(message = MeetingDate.MESSAGE_VALID_FORMAT)
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    boolean isEmpty() {
        return value.isEmpty();
    }

    boolean isValidAsDomain() {
        if (isEmpty()) return false;
        try {
            toMeetingDate();
            return true;
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }
    }

    MeetingDate toMeetingDate() {
        return MeetingDate.of(value);
    }

    public String value() {
        return value;
    }
}
