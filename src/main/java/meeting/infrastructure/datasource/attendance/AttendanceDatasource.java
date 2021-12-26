package meeting.infrastructure.datasource.attendance;

import meeting.application.service.attendance.AttendanceRepository;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDatasource implements AttendanceRepository {
    @Override
    public AttendanceId input(AttendanceInputRequest attendanceInputRequest) {
        return null;
    }
}
