package example.domain.model.meeting;

/**
 * 出欠回答番号
 */
public class AttendanceReplyNumber {

    String value;

    public AttendanceReplyNumber(String value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public AttendanceReplyNumber() {
    }

    @Override
    public String toString() {
        return value;
    }
}
