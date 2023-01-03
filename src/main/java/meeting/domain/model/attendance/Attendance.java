package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;

import javax.validation.Valid;

/**
 * 出欠
 */
public class Attendance {

    @Valid
    AttendanceNumber attendanceNumber;

    @Valid
    ParticipantNumber participantNumber;

    @Valid
    ParticipantName participantName;

    @Valid
    Conveniences conveniences;

    @Valid
    AttendanceComment attendanceComment;

    @Deprecated
    Attendance() {
    }

    public Attendance(AttendanceNumber attendanceNumber,
                      ParticipantNumber participantNumber,
                      ParticipantName participantName,
                      Conveniences conveniences,
                      AttendanceComment attendanceComment) {
        this.attendanceNumber = attendanceNumber;
        this.participantNumber = participantNumber;
        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
    }

    public static Attendance of(AttendanceNumber attendanceNumber,
                                ParticipantNumber participantNumber,
                                ParticipantName participantName,
                                Conveniences conveniences,
                                AttendanceComment attendanceComment) {
        return new Attendance(
                attendanceNumber,
                participantNumber,
                participantName,
                conveniences,
                attendanceComment);
    }

    public static Attendance of(AttendanceNumber attendanceNumber,
                                ParticipantNumber participantNumber,
                                ParticipantName participantName,
                                Conveniences conveniences) {
        return new Attendance(
                attendanceNumber,
                participantNumber,
                participantName,
                conveniences,
                AttendanceComment.empty());
    }

    public AttendanceNumber attendanceNumber() {
        return attendanceNumber;
    }

    public ParticipantNumber participantNumber() {
        return participantNumber;
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

    @Override
    public String toString() {
        return """
                {
                    "attendanceNumber": "%s",
                    "participantNumber": "%s",
                    "participantName": "%s",
                    "conveniences": %s,
                    "attendanceComment": "%s"
                }
                """.formatted(attendanceNumber.value(),
                participantNumber.value(),
                participantName.value(),
                conveniences.toString(),
                attendanceComment.toString());
    }
}
