package meeting.application.service.attendance;

import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;

/**
 * 出欠 リポジトリ
 */
public interface AttendanceRepository {
    AttendanceId input(AttendanceInputRequest attendanceInputRequest);
}
