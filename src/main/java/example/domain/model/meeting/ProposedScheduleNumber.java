package example.domain.model.meeting;

/**
 * 候補日程番号
 */
public class ProposedScheduleNumber {

    String value;

    public ProposedScheduleNumber(String value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public ProposedScheduleNumber() {
    }

    @Override
    public String toString() {
        return value;
    }
}
