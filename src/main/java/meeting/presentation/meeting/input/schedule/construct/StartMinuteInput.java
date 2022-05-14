package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartMinute;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 開始分（入力用）
 */
public class StartMinuteInput {

    String value;

    @AssertTrue(message = "開始分は30分単位で入力してください。")
    public boolean isValid() {
        if (Objects.isNull(value)) return true;
        if (value.isEmpty()) return true;
        if (isNotNumeric()) return false;

        return isValidAsDomain();
    }

    private boolean isNotNumeric() {
        return !(value.chars().allMatch(Character::isDigit));
    }

    private boolean isValidAsDomain() {
        try {
            StartMinute.fromValue(Integer.valueOf(value));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public StartMinuteInput(String value) {
        this.value = value;
    }

    @Deprecated
    StartMinuteInput() {}

    public static StartMinuteInput empty() {
        return new StartMinuteInput("");
    }

    public String value() {
        return value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean isSame(StartMinuteInput other) {
        return this.value.equals(other.value);
    }
}
