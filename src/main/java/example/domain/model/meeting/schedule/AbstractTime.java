package example.domain.model.meeting.schedule;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 　抽象的な時刻
 */
public abstract class AbstractTime {

    LocalTime value;

    public AbstractTime(LocalTime value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public AbstractTime() {
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String formattedString() {
        return value.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
