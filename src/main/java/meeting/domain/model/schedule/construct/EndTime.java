package meeting.domain.model.schedule.construct;

import meeting.domain.type.time.TimeFormatter;

import java.time.LocalTime;

/**
 * 終了時刻
 */
public class EndTime {

    LocalTime value;

    @Deprecated
    EndTime() {}

    public EndTime(final LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return value;
    }

    public String show() {
        return value.format(TimeFormatter.ofPattern());
    }
}
