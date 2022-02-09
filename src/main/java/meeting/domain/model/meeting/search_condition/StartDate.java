package meeting.domain.model.meeting.search_condition;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 検索開始日
 */
public class StartDate {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    public StartDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return this.value;
    }

    public static StartDate empty() {
        return new StartDate(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated
    public StartDate() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof StartDate other)) return false;
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
        return value.toString();
    }
}
