package meeting.domain.model.attendance;

import java.util.Objects;

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

    public AttendanceCommentInputStatus inputStatus() {
        if (Objects.isNull(value)) return AttendanceCommentInputStatus.なし;
        if (value.isBlank()) return AttendanceCommentInputStatus.なし;
        return AttendanceCommentInputStatus.あり;
    }

    @Deprecated
    AttendanceComment() {
    }
}
