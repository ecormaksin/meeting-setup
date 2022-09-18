package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;

import javax.validation.Valid;

/**
 * 出欠登録依頼
 */
public abstract class AttendanceRegistrationRequest {

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

    public AttendanceRegistrationRequest(
            AttendanceNumber attendanceNumber,
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

    @Deprecated
    AttendanceRegistrationRequest() {}

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
}
