package example.domain.model.schedule;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * ミーティング開催日
 */
public class MeetingDate {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    public MeetingDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return this.value;
    }

    public boolean isNull() {
        return Objects.isNull(value);
    }

    @Deprecated
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
