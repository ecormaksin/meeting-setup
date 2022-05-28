package meeting.domain.model.meeting.search_condition;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 検索終了日
 */
public class EndDate {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "検索終了日を入力してください。")
    LocalDate value;

    @Deprecated
    EndDate() {}

    public EndDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return this.value;
    }

    public static EndDate empty() {
        return new EndDate(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof EndDate other)) return false;
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
