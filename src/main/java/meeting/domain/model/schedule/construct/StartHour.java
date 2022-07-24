package meeting.domain.model.schedule.construct;

import meeting.domain.ValidationConcern;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 開始時刻
 */
public class StartHour extends ValidationConcern {

    public static final int MIN = 9;
    public static final int MAX = 17;

    public static final String MESSAGE = "開始時間は" + MIN + "時から" + MAX + "時の間で指定してください。";

    @Range(min = MIN, max = MAX, message = MESSAGE)
    int value;

    @Deprecated
    StartHour() {}

    public StartHour(final int value) {
        this.value = value;
        this.validate(this);
    }

    public static StartHour of(final int value) {
        return new StartHour(value);
    }

    public static StartHour of(final String stringValue) {
        return new StartHour(Integer.valueOf(stringValue));
    }

    public boolean isSame(@NotNull @Valid StartHour other) {
        return this.value == other.value;
    }

    public int value() {
        return value;
    }

    public String formattedValue() {
        return String.format("%02d", value);
    }

    public String stringValue() {
        return String.valueOf(value);
    }
}
