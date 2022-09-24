package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;

import javax.validation.constraints.AssertTrue;

/**
 * 会議時間（入力用）
 */
public class DurationInput {

    String value;

    boolean valid;

    @Deprecated
    DurationInput() {}

    public DurationInput(String value) {
        this.value = value;

        valid = isValid();
    }

    static DurationInput empty() {
        return new DurationInput("");
    }

    public static DurationInput of(String value) {
        return new DurationInput(value);
    }

    public static DurationInput of(int intValue) {
        return of(String.valueOf(intValue));
    }

    public static DurationInput of(Duration duration) {
        return of(duration.value());
    }

    @AssertTrue(message = "会議時間は30分単位・2時間以内で入力してください。")
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    boolean isEmpty() {
        return value.isEmpty();
    }

    boolean isValidAsDomain() {
        try {
            toDuration();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    Duration toDuration() {
        return Duration.of(value);
    }

    public String value() {
        return value;
    }
}
