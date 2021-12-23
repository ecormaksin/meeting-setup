package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;

/**
 * 出欠
 */
public class Attendance {
    AttendanceId attendanceId;
    ParticipantName participantName;
    Conveniences conveniences;
    AttendanceComment attendanceComment;

    public Attendance(AttendanceId attendanceId,
                      ParticipantName participantName,
                      Conveniences conveniences,
                      AttendanceComment attendanceComment) {
        this.attendanceId = attendanceId;
        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
    }

    @Deprecated
    public Attendance() {}

    public Conveniences attendances() {
        return conveniences;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", participantName=" + participantName +
                ", conveniences=" + conveniences +
                ", attendanceComment=" + attendanceComment +
                '}';
        // @formatter:on
    }
}
