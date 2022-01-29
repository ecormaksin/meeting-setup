package meeting.application.service.attendance;

import meeting.domain.model.attendance.*;
import meeting.domain.model.meeting.MeetingId;

/**
 * 出欠 リポジトリ
 */
public interface AttendanceRepository {
    ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingId meetingId, ParticipantName participantName);

    ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingId meetingId,
                                                                      AttendanceId attendanceId,
                                                                      ParticipantName participantName);

    AttendanceId input(MeetingId meetingId, AttendanceInputRequest attendanceInputRequest);

    Attendance attendanceOf(AttendanceId attendanceId);

    void edit(MeetingId meetingId, AttendanceEditRequest attendanceEditRequest);
}
