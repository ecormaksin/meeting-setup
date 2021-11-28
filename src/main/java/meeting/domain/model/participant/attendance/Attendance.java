package meeting.domain.model.participant.attendance;

import meeting.domain.model.schedule.ScheduleNumber;

/**
 * 出欠
 */
public class Attendance {
    ScheduleNumber scheduleNumber;
    AttendanceCategory attendanceCategory;

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
