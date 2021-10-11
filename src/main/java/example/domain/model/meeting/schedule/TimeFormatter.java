package example.domain.model.meeting.schedule;

import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    public static DateTimeFormatter ofPattern() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}
