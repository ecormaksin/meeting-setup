package meeting.application.service.attendance;

import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import org.springframework.stereotype.Service;

/**
 * 出欠の記録
 */
@Service
public class AttendanceRecordService {
    AttendanceRepository attendanceRepository;

    public AttendanceRecordService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceId input(AttendanceInputRequest attendanceInputRequest) {
        return attendanceRepository.input(attendanceInputRequest);
    }
}
