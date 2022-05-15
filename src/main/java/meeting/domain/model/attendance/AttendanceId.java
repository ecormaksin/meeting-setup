package meeting.domain.model.attendance;

import java.util.Objects;
import java.util.UUID;

/**
 * 出欠番号
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

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof AttendanceId other)) return false;
        if (Objects.isNull(other.value)) return false;
        return other.value.equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
