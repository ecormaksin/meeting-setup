package example.domain.model.meeting.schedule;

/**
 * スケジュール
 */
public class Schedule {
    MeetingDate 開催日;
    StartTime 開始時刻;
    EndTime 終了時刻;

    @Override
    public String toString() {
        // @formatter:off
        return "Schedule{" +
                "開催日=" + 開催日 +
                ", 開始時刻=" + 開始時刻 +
                ", 終了時刻=" + 終了時刻 +
                "}";
        // @formatter:on
    }
}
