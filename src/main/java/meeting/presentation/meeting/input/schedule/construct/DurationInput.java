package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 会議時間（入力用）
 */
public class DurationInput {

    String value;

    public DurationInput(String value) {
        this.value = value;
    }

    @Deprecated
    DurationInput() {}

    @AssertTrue(message = "会議時間は30分単位・2時間以内で入力してください。")
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
            Duration.fromValue(Integer.valueOf(value));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static DurationInput empty() {
        return new DurationInput("");
    }

    public String value() {
        return value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean isSame(DurationInput other) {
        return this.value.equals(other.value);
    }
}
