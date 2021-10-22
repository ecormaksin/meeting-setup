package example.domain.model.participant.attendance;

import example.domain.model.schedule.ScheduleNumber;

/**
 * 出欠
 */
public class Attendance {
    ScheduleNumber スケジュール番号;
    AttendanceCategory 出欠種別;

    public String ScheduleNumber() {
        return スケジュール番号.toString();
    }

    public String attendanceCategorySymbol() {
        return 出欠種別.記号();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Attendance{" +
                "スケジュール番号=" + スケジュール番号 +
                ", 出欠種別=" + 出欠種別 +
                '}';
        // @formatter:on
    }
}
