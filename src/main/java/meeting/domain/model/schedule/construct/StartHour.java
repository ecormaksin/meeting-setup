package meeting.domain.model.schedule.construct;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 開始時刻
 */
public class StartHour {

    public static final int MIN = 9;
    public static final int MAX = 17;

    public static final String MESSAGE = "開始時間は" + MIN + "時から" + MAX + "時の間で指定してください。";

    @Min(value = MIN, message = MESSAGE)
    @Max(value = MAX, message = MESSAGE)
    int value;

    public StartHour(int value) {
        this.value = value;
    }

    @Deprecated
    StartHour() {}

    public boolean isSame(StartHour other) {
        return this.value == other.value;
    }
}
