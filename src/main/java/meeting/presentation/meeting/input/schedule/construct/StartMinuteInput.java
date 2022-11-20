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

    public static StartMinuteInput empty() {
        return new StartMinuteInput("");
    }

    public static StartMinuteInput of(StartMinute startMinute) {
        return new StartMinuteInput(startMinute.stringValue());
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
