package meeting.domain.model.attendance;

import java.util.UUID;

/**
 * 出欠ID
 */
public class AttendanceId {

    String value;

    public static AttendanceId newId() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return new AttendanceId(uuidStr.replace("-", ""));
    }

    public AttendanceId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Deprecated
    public AttendanceId() {
    }

    @Override
    public String toString() {
        return value;
    }
}
