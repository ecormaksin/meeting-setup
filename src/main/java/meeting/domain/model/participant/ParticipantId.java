package meeting.domain.model.participant;

/**
 * 参加者ID
 */
public class ParticipantId {

    String value;

    public ParticipantId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Deprecated
    public ParticipantId() {
    }

    @Override
    public String toString() {
        return value;
    }
}
