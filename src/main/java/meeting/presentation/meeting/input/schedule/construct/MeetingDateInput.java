package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.MeetingDate;

import javax.validation.constraints.AssertTrue;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * 開催日（入力用）
 */
public class MeetingDateInput implements Element {

    String value;

    @Deprecated
    MeetingDateInput() {}

    public MeetingDateInput(String value) {
        this.value = value;
    }

    static MeetingDateInput empty() {
        return new MeetingDateInput("");
    }

    public static MeetingDateInput fromDomainObject(final MeetingDate meetingDate) {
        return new MeetingDateInput(meetingDate.stringValue());
    }

    @AssertTrue(message = "開催日は'yyyy-MM-dd'形式で入力してください。")
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
            toDomainObject();
            return true;
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(MeetingDateInput other) {
        return this.value.equals(other.value);
    }

    public MeetingDate toDomainObject() {
        return MeetingDate.fromString(value);
    }

    public String value() {
        return value;
    }
}
