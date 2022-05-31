package meeting.application.service.attendance;

import meeting.domain.model.attendance.AttendanceEditRequest;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.AttendanceInputRequest;
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

    public AttendanceNumber input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        return attendanceRepository.input(meetingNumber, attendanceInputRequest);
    }

    public void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest) {
        attendanceRepository.edit(meetingNumber, attendanceEditRequest);
    }

    public void delete(AttendanceNumber attendanceNumber) {
        attendanceRepository.delete(attendanceNumber);
    }
}
