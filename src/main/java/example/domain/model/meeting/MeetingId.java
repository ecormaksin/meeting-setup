package example.domain.model.meeting;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * ミーティングID
 */
public class MeetingId {
    @NotBlank(message = "{example.domain.model.meeting.MeetingId.NotBlank.message}")
    String value;

    public static MeetingId newId() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return new MeetingId(uuidStr.replace("-", ""));
    }

    public MeetingId(String value) {
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
    MeetingId() {}
}
