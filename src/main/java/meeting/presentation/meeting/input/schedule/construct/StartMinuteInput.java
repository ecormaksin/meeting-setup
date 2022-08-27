package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartMinute;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 開始分（入力用）
 */
public class StartMinuteInput {

    String value;

    boolean valid;

    @Deprecated
    StartMinuteInput() {}

    public StartMinuteInput(String value) {
        this.value = value;

        valid = isValid();
    }

    static StartMinuteInput empty() {
        return new StartMinuteInput("");
    }

    public static StartMinuteInput of(final String value) {
        return new StartMinuteInput(value);
    }

    public static StartMinuteInput of(final int intValue) {
        return of(String.valueOf(intValue));
    }

    public static StartMinuteInput of(final StartMinute startMinute) {
        return of(startMinute.value());
    }

    @AssertTrue(message = "開始分は30分単位で入力してください。")
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    boolean isEmpty() {
        if (Objects.isNull(value)) return true;
        return value.isEmpty();
    }

    boolean isValidAsDomain() {
        if (isEmpty()) return false;
        try {
            toStartMinute();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    StartMinute toStartMinute() {
        return StartMinute.of(value);
    }

    public String value() {
        return value;
    }
}
