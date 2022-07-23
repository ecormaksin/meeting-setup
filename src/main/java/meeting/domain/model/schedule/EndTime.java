package meeting.domain.model.schedule;

import java.time.LocalTime;

/**
 * 終了時刻
 */
public class EndTime {

    LocalTime value;

    public EndTime(final LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return value;
    }
}
