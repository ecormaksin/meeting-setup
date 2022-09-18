package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;

/**
 * 出欠入力依頼
 */
public class AttendanceInputRequest extends AttendanceRegistrationRequest {

    public AttendanceInputRequest(
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber,
            ParticipantName participantName,
            Conveniences conveniences,
            AttendanceComment attendanceComment) {

        super(attendanceNumber,
            participantNumber,
            participantName,
            conveniences,
            attendanceComment);
    }

    @Deprecated
    AttendanceInputRequest() {}
}
