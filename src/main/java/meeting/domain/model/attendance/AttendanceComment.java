package meeting.domain.model.attendance;

/**
 * 出欠コメント
 */
public class AttendanceComment {
    String value;

    public AttendanceComment(String value) {
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
    AttendanceComment() {
    }
}
