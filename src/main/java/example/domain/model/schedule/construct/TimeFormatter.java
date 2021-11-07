package example.domain.model.schedule.construct;

import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    public static DateTimeFormatter ofPattern() {
        return DateTimeFormatter.ofPattern("HH:mm");
    }
}
