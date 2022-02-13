package meeting.domain.model.meeting.meeting_list;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 最も早い開始日時
 */
public class EarliestStartDateTime {

    LocalDateTime value;

    public EarliestStartDateTime(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime value() {
        return this.value;
    }

    public static EarliestStartDateTime empty() {
        return new EarliestStartDateTime(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated
    public EarliestStartDateTime() {
    }

    public String show() {
        return value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E) HH:mm"));
    }
}
