package meeting.domain.model.participant;

/**
 * 参加者の合計人数
 */
public class TotalNumberOfParticipant {
    int value;

    public TotalNumberOfParticipant(int value) {
        this.value = value;
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
