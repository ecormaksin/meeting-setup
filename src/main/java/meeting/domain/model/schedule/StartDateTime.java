package meeting.domain.model.schedule;

import java.time.LocalDateTime;

/**
 * 開始日時
 */
public class StartDateTime {

    LocalDateTime value;

    public StartDateTime(final LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime value() {
        return value;
    }
}
