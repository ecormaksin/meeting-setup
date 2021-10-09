package example.domain.model.meeting.schedule;

import java.time.LocalTime;

/**
 * 開始時刻
 */
public class StartTime extends AbstractTime {

    public StartTime(LocalTime value) {
        super(value);
    }

    @Deprecated(since = "MyBatis")
    public StartTime() {
        super();
    }
}
