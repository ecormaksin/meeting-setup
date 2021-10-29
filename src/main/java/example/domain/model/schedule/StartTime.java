package example.domain.model.schedule;

import java.time.LocalTime;

/**
 * 開始時刻
 */
public class StartTime {

    LocalTime value;

    public StartTime(LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return this.value;
    }

    @Deprecated(since = "MyBatis")
    public StartTime() {
    }

    @Override
    public String toString() {
        return value.format(TimeFormatter.ofPattern());
    }
}
