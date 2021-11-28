package meeting.domain.model.participant;

/**
 * 参加者名
 */
public class ParticipantName {
    String value;

    public ParticipantName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Deprecated
    ParticipantName() {
    }
}
