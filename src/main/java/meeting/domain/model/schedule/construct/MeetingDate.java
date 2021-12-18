package meeting.domain.model.schedule.construct;

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
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof MeetingDate other)) return false;
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
        return value.toString();
    }

    public String show() {
        return value.format(DateTimeFormatter.ofPattern("MM/dd(E)"));
    }
}
