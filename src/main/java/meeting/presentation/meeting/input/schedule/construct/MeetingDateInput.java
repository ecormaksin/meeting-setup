package meeting.presentation.meeting.input.schedule.construct;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * ミーティング開催日（入力用）
 */
public class MeetingDateInput {

    String value;

    public MeetingDateInput(String value) {
        this.value = value;
    }

    @Deprecated
    MeetingDateInput() {}

    @AssertTrue(message = "ミーティング開催日は'yyyy-MM-dd'形式で入力してください。")
    public boolean isValid() {
        if (Objects.isNull(value)) return true;
        if (value.isEmpty()) return true;

        return isValidAsDomain();
    }

    private boolean isValidAsDomain() {
        try {
            LocalDate.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static MeetingDateInput empty() {
        return new MeetingDateInput("");
    }

    public String value() {
        return value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean isSame(MeetingDateInput other) {
        return this.value.equals(other.value);
    }
}
