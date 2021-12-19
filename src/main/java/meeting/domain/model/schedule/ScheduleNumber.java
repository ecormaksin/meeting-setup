package meeting.domain.model.schedule;

import java.util.Objects;

/**
 * 日程候補番号
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

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof ScheduleNumber other)) return false;
        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
