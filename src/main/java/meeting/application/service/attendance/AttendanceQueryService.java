package meeting.application.service.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceId;
import org.springframework.stereotype.Service;

/**
 * 出欠の参照
 */
@Service
public class AttendanceQueryService {
    AttendanceRepository attendanceRepository;

    public AttendanceQueryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance attendanceOf(AttendanceId attendanceId) {
        return attendanceRepository.attendanceOf(attendanceId);
    }
}
