package meeting.domain.model.schedule.construct;

import java.time.LocalTime;

/**
 * 開始時刻
 */
public class StartTime {

    LocalTime value;

    public StartTime(final LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return value;
    }
}
