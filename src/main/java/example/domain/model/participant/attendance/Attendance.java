package example.domain.model.participant.attendance;

import example.domain.model.schedule.ScheduleNumber;

/**
 * 出欠
 */
public class Attendance {
    ScheduleNumber scheduleNumber;
    AttendanceCategory attendanceCategory;

    public String ScheduleNumber() {
        return scheduleNumber.toString();
    }

    public String attendanceCategorySymbol() {
        return attendanceCategory.symbol();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Attendance{" +
                "scheduleNumber=" + scheduleNumber +
                ", attendanceCategory=" + attendanceCategory +
                '}';
        // @formatter:on
    }
}
