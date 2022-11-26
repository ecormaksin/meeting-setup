package meeting.domain.model.schedule.construct;

import meeting.domain.ValidationConcern;
import org.hibernate.validator.constraints.Range;

/**
 * 開始時刻
 */
public class StartHour extends ValidationConcern {

    public static final int MIN = 9;
    public static final int MAX = 17;

    public static final String BETWEEN_SPECIFIED_RANGE = "開始時間は" + MIN + "時から" + MAX + "時の間で指定してください。";

    @Range(min = MIN, max = MAX, message = BETWEEN_SPECIFIED_RANGE)
    int value;

    @Deprecated
    StartHour() {}

    public StartHour(int value) {
        this.value = value;
        this.validate(this);
    }

    public static StartHour of(int value) {
        return new StartHour(value);
    }

    public static StartHour of(String stringValue) {
        return new StartHour(Integer.valueOf(stringValue));
    }

    public boolean isSame(StartHour other) {
        return this.value == other.value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
