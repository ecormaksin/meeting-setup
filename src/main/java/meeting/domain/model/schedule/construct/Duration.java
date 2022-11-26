package meeting.domain.model.schedule.construct;

import java.util.ArrayList;
import java.util.List;

/**
 * 会議時間
 */
public enum Duration {

    OF_30_MINUTES(30, "30分"),
    OF_1_HOUR(60, "1時間"),
    OF_1_HOUR_30_MINUTES(90, "1時間30分"),
    OF_2_HOURS(120, "2時間");

    int value;
    String label;

    Duration(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Duration of(int value) {
        List<String> candidates = new ArrayList<>();
        for (Duration duration : Duration.values()) {
            if (duration.value == value) {
                return duration;
            }
            candidates.add(duration.label);
        }
        String candidatesString = String.join(",", candidates);
        throw new IllegalArgumentException(
                String.format("会議時間は次の中から指定してください。: %s", candidatesString)
        );
    }

    public static Duration of(String strigValue) {
        return of(Integer.valueOf(strigValue));
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

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
