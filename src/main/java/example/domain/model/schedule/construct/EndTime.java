package example.domain.model.schedule.construct;

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

    public boolean isNull() {
        return Objects.isNull(value);
    }

    @Deprecated
    public EndTime() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof EndTime other)) return false;
        if (this.isNull()) return false;
        if (other.isNull()) return false;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.format(TimeFormatter.ofPattern());
    }
}
