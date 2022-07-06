package meeting.domain.model.schedule;

import java.time.LocalDateTime;

/**
 * 終了日時
 */
public class EndDateTime {

    LocalDateTime value;

    public EndDateTime(final LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime value() {
        return value;
    }
}
