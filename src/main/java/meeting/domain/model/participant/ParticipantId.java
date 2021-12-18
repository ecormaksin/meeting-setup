package meeting.domain.model.participant;

import java.util.UUID;

/**
 * 参加者ID
 */
public class ParticipantId {

    String value;

    public static ParticipantId newId() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return new ParticipantId(uuidStr.replace("-", ""));
    }

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
