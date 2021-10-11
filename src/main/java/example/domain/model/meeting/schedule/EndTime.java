package example.domain.model.meeting.schedule;

import java.time.LocalTime;

/**
 * 終了時刻
 */
public class EndTime {

    LocalTime value;

    public EndTime(LocalTime value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public EndTime() {
    }

    @Override
    public String toString() {
        return value.format(TimeFormatter.ofPattern());
    }
}
