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

    public static EndDate empty() {
        return new EndDate(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated(since = "thymeleaf")
    public void value() {}

    @Override
    public String toString() {
        return value.toString();
    }

    public static EndDate parse(String dateText) {
        return new EndDate(LocalDate.parse(dateText));
    }
}
