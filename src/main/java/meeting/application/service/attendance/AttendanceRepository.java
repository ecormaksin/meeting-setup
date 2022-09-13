package meeting.application.service.attendance;

import meeting.domain.model.attendance.*;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;

/**
 * 出欠 リポジトリ
 */
public interface AttendanceRepository {
    ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingNumber meetingNumber, ParticipantName participantName);

    ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingNumber meetingNumber,
                                                                      AttendanceNumber attendanceNumber,
                                                                      ParticipantName participantName);

    AttendanceNumber input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest);

    Attendance attendanceOf(AttendanceNumber attendanceNumber);

    void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest);

    void delete(AttendanceNumber attendanceNumber);
}
