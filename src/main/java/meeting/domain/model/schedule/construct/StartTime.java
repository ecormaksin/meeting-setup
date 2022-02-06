package meeting.domain.model.schedule.construct;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 開始時刻
 */
public class StartTime {

    @DateTimeFormat(pattern = TimeFormatter.PATTERN)
    LocalTime value;

    public StartTime(LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return this.value;
    }

    public static StartTime empty() {
        return new StartTime(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated
    public StartTime() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof StartTime other)) return false;
        if (this.isEmpty()) return false;
        if (other.isEmpty()) return false;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return show();
    }

    public String show() {
        return value.format(TimeFormatter.ofPattern());
    }
}
