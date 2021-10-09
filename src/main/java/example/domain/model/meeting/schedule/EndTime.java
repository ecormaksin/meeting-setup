package example.domain.model.meeting.schedule;

import java.time.LocalTime;

/**
 * 終了時刻
 */
public class EndTime extends AbstractTime {

    public EndTime(LocalTime value) {
        super(value);
    }

    @Deprecated(since = "MyBatis")
    public EndTime() {
        super();
    }
}
