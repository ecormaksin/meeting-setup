package meeting.domain.model.meeting.search_condition;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 検索開始日
 */
public class StartDate {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "検索開始日を入力してください。")
    LocalDate value;

    @Deprecated
    StartDate() {}

    public StartDate(LocalDate value) {
        this.value = value;
    }

    public static StartDate empty() {
        return new StartDate(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    public boolean isBeforeOrSame(EndDate endDate) {
        LocalDate endDateValue = endDate.value();
        return  value.isBefore(endDateValue) || value.equals(endDateValue);
    }

    public LocalDate value() {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
