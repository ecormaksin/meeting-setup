package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartMinute;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 開始分（入力用）
 */
public class StartMinuteInput implements Element {

    String value;

    @Deprecated
    StartMinuteInput() {}

    public StartMinuteInput(String value) {
        this.value = value;
    }

    static StartMinuteInput empty() {
        return new StartMinuteInput("");
    }

    public static StartMinuteInput fromDomainObject(final StartMinute startMinute) {
        final int intValue = startMinute.value();
        final String stringValue = String.valueOf(intValue);
        return new StartMinuteInput(stringValue);
    }

    @AssertTrue(message = "開始分は30分単位で入力してください。")
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
            toStartMinute();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(StartMinuteInput other) {
        return this.value.equals(other.value);
    }

    public StartMinute toStartMinute() {
        return StartMinute.fromString(value);
    }

    public String value() {
        return value;
    }
}
