package meeting.application.service.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceEditRequest;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.meeting.MeetingId;

/**
 * 出欠 リポジトリ
 */
public interface AttendanceRepository {
    AttendanceId input(MeetingId meetingId, AttendanceInputRequest attendanceInputRequest);
    Attendance attendanceOf(AttendanceId attendanceId);
    void edit(MeetingId meetingId, AttendanceEditRequest attendanceEditRequest);
}
