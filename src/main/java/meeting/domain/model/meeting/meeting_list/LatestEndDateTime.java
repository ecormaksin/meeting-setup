package meeting.domain.model.meeting.meeting_list;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 最も遅い終了日時
 */
public class LatestEndDateTime {

    LocalDateTime value;

    public LatestEndDateTime(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime value() {
        return this.value;
    }

    public static LatestEndDateTime empty() {
        return new LatestEndDateTime(null);
    }

    public boolean isEmpty() {
        return Objects.isNull(value);
    }

    @Deprecated
    public LatestEndDateTime() {
    }

    public String show() {
        return value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd(E) HH:mm"));
    }
}
