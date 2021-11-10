package example.domain.model.schedule.construct;

import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    public static final String PATTERN = "HH:mm";

    public static DateTimeFormatter ofPattern() {
        return DateTimeFormatter.ofPattern(PATTERN);
    }
}
