package meeting.application.service.attendance;

import meeting.domain.model.attendance.AttendanceEditRequest;
import meeting.domain.model.attendance.AttendanceId;
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

    public AttendanceId input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        return attendanceRepository.input(meetingNumber, attendanceInputRequest);
    }

    public void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest) {
        attendanceRepository.edit(meetingNumber, attendanceEditRequest);
    }

    public void delete(AttendanceId attendanceId) {
        attendanceRepository.delete(attendanceId);
    }
}
