package meeting.domain.model.attendance;

/**
 * 参加者の合計人数
 */
public class TotalNumberOfParticipant {
    int value;

    public TotalNumberOfParticipant(int value) {
        this.value = value;
    }

    public static TotalNumberOfParticipant of(int value) {
        return new TotalNumberOfParticipant(value);
    }

    public boolean isGreaterThanOne() {
        return value > 1;
    }

    public boolean isGreaterThan(final TotalNumberOfParticipant other) {
        return this.value > other.value;
    }

    public boolean isSame(final TotalNumberOfParticipant other) {
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
