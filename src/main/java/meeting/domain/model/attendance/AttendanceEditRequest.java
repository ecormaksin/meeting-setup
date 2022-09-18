package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;

/**
 * 出欠編集依頼
 */
public class AttendanceEditRequest extends AttendanceRegistrationRequest {

    public AttendanceEditRequest(
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
    AttendanceEditRequest() {}
}
