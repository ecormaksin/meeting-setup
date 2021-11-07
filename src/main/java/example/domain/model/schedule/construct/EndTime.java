package example.domain.model.schedule.construct;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 終了時刻
 */
public class EndTime {

    @DateTimeFormat(pattern = "HH:mm")
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
    public String toString() {
        return value.format(TimeFormatter.ofPattern());
    }
}
