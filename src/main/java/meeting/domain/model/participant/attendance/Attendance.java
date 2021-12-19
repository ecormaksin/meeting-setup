package meeting.domain.model.participant.attendance;

import meeting.domain.model.schedule.ScheduleNumber;

/**
 * 出欠
 */
public class Attendance {
    ScheduleNumber scheduleNumber;
    AttendanceCategory attendanceCategory;

    public static Attendance of(int primitiveScheduleNumber,
                                String symbol) {
        return new Attendance(new ScheduleNumber(primitiveScheduleNumber),
                                AttendanceCategory.of(symbol));
    }

    public Attendance(ScheduleNumber scheduleNumber,
                       AttendanceCategory attendanceCategory) {
        this.scheduleNumber = scheduleNumber;
        this.attendanceCategory = attendanceCategory;
    }

    public ScheduleNumber scheduleNumber() {
        return scheduleNumber;
    }

    public AttendanceCategory attendanceCategory() {
        return attendanceCategory;
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
