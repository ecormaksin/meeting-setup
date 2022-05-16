package meeting.domain.model.schedule;

import library.ValidationConcern;

import javax.validation.constraints.Min;

/**
 * 日程番号
 */
public class ScheduleNumber extends ValidationConcern {

    @Min(value = 0, message = "日程番号には自然数を指定してください。")
    int value;

    @Deprecated
    ScheduleNumber() {}

    public ScheduleNumber(final int value) {
        this.value = value;
        this.validate(this);
    }

    public boolean issued() {
        return value >= 0;
    }
    public static ScheduleNumber initialize() {
        return new ScheduleNumber(0);
    }

    public int value() {
        return value;
    }
}
