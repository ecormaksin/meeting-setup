package example.domain.model.schedule;

/**
 * 候補日程番号
 */
public class ScheduleNumber {
    int value;

    public ScheduleNumber(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Deprecated
    ScheduleNumber() {
    }
}
