package meeting.domain.model.schedule.construct;

import java.util.ArrayList;
import java.util.List;

/**
 * 開始分
 */
public enum StartMinute {

    JUST(0, "00"),
    HALF(30, "30");

    int value;
    String label;

    StartMinute(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static StartMinute of(int value) {
        List<String> candidates = new ArrayList<>();
        for (StartMinute startMinute : StartMinute.values()) {
            if (startMinute.value == value) {
                return startMinute;
            }
            candidates.add(String.valueOf(startMinute.value));
        }
        String candidatesString = String.join(",", candidates);
        throw new IllegalArgumentException(
                String.format("開始分は次の中から指定してください。: %s", candidatesString)
        );
    }

    public static StartMinute of(String stringValue) {
        return of(Integer.valueOf(stringValue));
    }

    public int value() {
        return value;
    }

    public String stringValue() {
        return String.valueOf(value);
    }

    public String label() {
        return label;
    }
}
