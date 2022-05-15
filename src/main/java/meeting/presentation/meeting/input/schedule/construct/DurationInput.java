package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 会議時間（入力用）
 */
public class DurationInput implements Element {

    String value;

    /**
     * @deprecated  Use DurationInput(String value) or empty()
     */
    @Deprecated
    DurationInput() {}

    public DurationInput(String value) {
        this.value = value;
    }

    static DurationInput empty() {
        return new DurationInput("");
    }

    @AssertTrue(message = "会議時間は30分単位・2時間以内で入力してください。")
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    public boolean isEmpty() {
        if (Objects.isNull(value)) return true;
        return value.isEmpty();
    }

    public boolean isValidAsDomain() {
        try {
            Duration.fromValue(Integer.valueOf(value));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(DurationInput other) {
        return this.value.equals(other.value);
    }

    public String value() {
        return value;
    }
}
