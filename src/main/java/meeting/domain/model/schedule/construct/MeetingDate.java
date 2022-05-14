package meeting.domain.model.schedule.construct;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * ミーティング開催日
 */
public class MeetingDate {

    @NotNull(message = "開催日は'yyyy-MM-dd'形式で必ず入力してください。")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    public MeetingDate(final LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return this.value;
    }

    public static MeetingDate empty() {
        return new MeetingDate(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated
    MeetingDate() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof MeetingDate other)) return false;
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

    public String show() {
        return value.format(DateTimeFormatter.ofPattern("MM/dd(E)"));
    }
}
