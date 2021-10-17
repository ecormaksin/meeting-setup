package example.domain.model.schedule;

/**
 * 日程番号
 */
public class ScheduleNumber {

    String value;

    public ScheduleNumber(String value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public ScheduleNumber() {
    }

    @Override
    public String toString() {
        return value;
    }
}
