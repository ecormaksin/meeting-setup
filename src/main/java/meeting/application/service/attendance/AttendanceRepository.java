package meeting.application.service.attendance;

import meeting.domain.model.attendance.*;
import meeting.domain.model.meeting.MeetingNumber;

/**
 * 出欠 リポジトリ
 */
public interface AttendanceRepository {
    ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingNumber meetingNumber, ParticipantName participantName);

    ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingNumber meetingNumber,
                                                                      AttendanceId attendanceId,
                                                                      ParticipantName participantName);

    AttendanceId input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest);

    Attendance attendanceOf(AttendanceId attendanceId);

    void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest);

    void delete(AttendanceId attendanceId);
}
