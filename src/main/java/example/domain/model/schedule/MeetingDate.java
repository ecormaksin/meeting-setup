package example.domain.model.schedule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ミーティング開催日
 */
public class MeetingDate {

    LocalDate value;

    public MeetingDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return this.value;
    }

    @Deprecated(since = "MyBatis")
    public MeetingDate() {
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public String formattedString() {
        return value.format(DateTimeFormatter.ofPattern("MM/dd(E)"));
    }
}
