package meeting.domain.model.meeting.search_condition;

public class LimitCount {
    int value;

    @Deprecated
    LimitCount() {}

    public LimitCount(int value) {
        this.value = value;
    }

    public static LimitCount of(int value) {
        return new LimitCount(value);
    }

    public static LimitCount defaultValue() {
        return LimitCount.of(20);
    }

    public int value() {
        return value;
    }
}
