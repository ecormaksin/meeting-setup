package meeting.domain.model.schedule.construct;

import java.util.ArrayList;
import java.util.List;

/**
 * 会議時間
 */
public enum Duration {

    HALF(30, "30分"),
    ONE_HOUR(60, "1時間"),
    ONE_HOUR_HALF(90, "1時間30分"),
    TWO_HOUR(120, "2時間");

    int value;
    String label;

    Duration(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Duration fromValue(final int value) {
        List<String> candidates = new ArrayList<>();
        for (Duration duration : Duration.values()) {
            if (duration.value == value) {
                return duration;
            }
            candidates.add(duration.label);
        }
        final String candidatesString = String.join(",", candidates);
        throw new IllegalArgumentException(
                String.format("会議時間は次の中から指定してください。: %s", candidatesString)
        );
    }

    public int value() {
        return value;
    }

    public String label() {
        return label;
    }
}
