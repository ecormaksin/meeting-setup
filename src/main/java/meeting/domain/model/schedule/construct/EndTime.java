package meeting.domain.model.schedule.construct;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 終了時刻
 */
public class EndTime {

    @DateTimeFormat(pattern = TimeFormatter.PATTERN)
    LocalTime value;

    public EndTime(LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return this.value;
    }

    public static EndTime empty() {
        return new EndTime(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated
    public EndTime() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof EndTime other)) return false;
        if (this.isEmpty()) return false;
        if (other.isEmpty()) return false;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public String show() {
        return value.format(TimeFormatter.ofPattern());
    }
}
