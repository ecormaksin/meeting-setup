package meeting.domain.model.schedule.construct;

import meeting.domain.type.time.TimeFormatter;

import java.time.LocalTime;

/**
 * 開始時刻
 */
public class StartTime {

    LocalTime value;

    @Deprecated
    StartTime() {}

    public StartTime(LocalTime value) {
        this.value = value;
    }

    public LocalTime value() {
        return value;
    }

    public String show() {
        return value.format(TimeFormatter.ofPattern());
    }
}
