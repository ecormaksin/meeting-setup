package meeting.domain.model.schedule;

import javax.validation.constraints.PositiveOrZero;

/**
 * 日程番号
 */
public class ScheduleNumber {

    @PositiveOrZero(message = "日程番号には自然数を指定してください。")
    int value;

    @Deprecated
    ScheduleNumber() {}

    public ScheduleNumber(int value) {
        this.value = value;
    }

    public static ScheduleNumber of(int value) {
        return new ScheduleNumber(value);
    }

    public ScheduleRegistrationStatus registrationStatus() {
        return value > 0 ?
                ScheduleRegistrationStatus.編集
                :
                ScheduleRegistrationStatus.追加;
    }
    public static ScheduleNumber initialized() {
        return new ScheduleNumber(0);
    }

    public boolean isSame(ScheduleNumber other) {
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
