package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 会議時間（入力用）
 */
public class DurationInput implements ScheduleConstructElement {

    @NotNull
    String value;

    /**
     * @deprecated  Use DurationInput(String value) or empty()
     */
    @Deprecated
    DurationInput() {}

    public DurationInput(final String value) {
        this.value = value;
    }

    static DurationInput empty() {
        return new DurationInput("");
    }

    public static DurationInput of(final String value) {
        return new DurationInput(value);
    }

    public static DurationInput ofMinute(final int intValue) {
        return of(String.valueOf(intValue));
    }

    public static DurationInput of(final Duration duration) {
        return ofMinute(duration.value());
    }

    @AssertTrue(message = "会議時間は30分単位・2時間以内で入力してください。")
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    public boolean isEmpty() {
        if (Objects.isNull(value)) return false;
        return value.isEmpty();
    }

    public boolean isValidAsDomain() {
        try {
            toDuration();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(DurationInput other) {
        return this.value.equals(other.value);
    }

    public Duration toDuration() {
        return Duration.fromString(value);
    }

    public String value() {
        return value;
    }
}
