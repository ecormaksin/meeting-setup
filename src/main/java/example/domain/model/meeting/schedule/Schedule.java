package example.domain.model.meeting.schedule;

/**
 * スケジュール
 */
public class Schedule {
    ScheduleNumber 日程番号;
    MeetingDate 開催日;
    StartTime 開始時刻;
    EndTime 終了時刻;

    @Override
    public String toString() {
        // @formatter:off
        return "Schedule{" +
                "日程番号=" + 日程番号 +
                ", 開催日=" + 開催日 +
                ", 開始時刻=" + 開始時刻 +
                ", 終了時刻=" + 終了時刻 +
                '}';
        // @formatter:on
    }
}
