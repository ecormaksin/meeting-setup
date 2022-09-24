package meeting.domain.model.attendance;

import javax.validation.constraints.NotNull;

/**
 * 出欠コメント
 */
public class AttendanceComment {

    @NotNull(message = "出欠コメントを入力してください。")
    String value;

    @Deprecated
    AttendanceComment() {}

    public AttendanceComment(String value) {
        this.value = value;
    }

    public static AttendanceComment of(String value) {
        return new AttendanceComment(value);
    }

    public String value() {
        return value;
    }

    public AttendanceCommentInputStatus inputStatus() {
        if (value.isBlank()) return AttendanceCommentInputStatus.なし;
        return AttendanceCommentInputStatus.あり;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
