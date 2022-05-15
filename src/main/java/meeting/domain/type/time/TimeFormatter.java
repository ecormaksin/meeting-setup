package meeting.domain.type.time;

import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    public static DateTimeFormatter ofPattern() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}
