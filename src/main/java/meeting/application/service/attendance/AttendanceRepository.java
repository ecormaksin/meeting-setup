package meeting.application.service.attendance;

import meeting.domain.model.attendance.*;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberDuplicationStatus;

/**
 * 出欠 リポジトリ
 */
public interface AttendanceRepository {
    ParticipantNumberDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantNumber participantNumber);

    ParticipantNumberDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber);

    ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantName participantName);

    ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantName participantName);

    void input(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest);

    Attendance attendanceOf(AttendanceNumber attendanceNumber);

    void edit(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest);

    void delete(AttendanceNumber attendanceNumber);
}
