package meeting.application.service.attendance;

import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.AttendanceRegistrationRequest;
import meeting.domain.model.meeting.MeetingNumber;
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

    public void input(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest) {
        attendanceRepository.input(meetingNumber, registrationRequest);
    }

    public void edit(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest) {
        attendanceRepository.edit(meetingNumber, registrationRequest);
    }

    public void delete(AttendanceNumber attendanceNumber) {
        attendanceRepository.delete(attendanceNumber);
    }
}
