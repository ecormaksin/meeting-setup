package example.domain.model.meeting;

/**
 * ミーティング番号
 */
public class MeetingNumber {

    String value;

    public MeetingNumber(String value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public MeetingNumber() {
    }

    @Override
    public String toString() {
        return value;
    }
}
