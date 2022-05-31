package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;

/**
 * 出欠
 */
public class Attendance {
    AttendanceNumber attendanceNumber;
    ParticipantName participantName;
    Conveniences conveniences;
    AttendanceComment attendanceComment;

    @Deprecated
    Attendance() {}

    public Attendance(AttendanceNumber attendanceNumber,
                      ParticipantName participantName,
                      Conveniences conveniences,
                      AttendanceComment attendanceComment) {
        this.attendanceNumber = attendanceNumber;
        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
    }

    public AttendanceNumber attendanceNumber() {
        return attendanceNumber;
    }

    public ParticipantName participantName() {
        return participantName;
    }

    public Conveniences conveniences() {
        return conveniences;
    }

    public AttendanceComment attendanceComment() {
        return attendanceComment;
    }
}
