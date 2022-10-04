package meeting.domain.model.attendance.convenience;

/**
 * 都合別の合計人数
 */
public class CountOfConvenience {
    int value;

    public CountOfConvenience(int value) {
        this.value = value;
    }

    public static CountOfConvenience of(int value) {
        return new CountOfConvenience(value);
    }

    boolean isNotAtLeastTwo() {
        return value <= 1;
    }

    boolean isGreaterThan(CountOfConvenience other) {
        return this.value > other.value;
    }

    boolean isGreaterThanOrSame(CountOfConvenience other) {
        return this.value >= other.value;
    }

    boolean isSame(CountOfConvenience other) {
        return this.value == other.value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public String show() {
        return String.format("%d人", value);
    }
}
