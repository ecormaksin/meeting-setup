package meeting.application.service.attendance;

import meeting.domain.model.attendance.AttendanceEditRequest;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.meeting.MeetingId;
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

    public AttendanceId input(MeetingId meetingId, AttendanceInputRequest attendanceInputRequest) {
        return attendanceRepository.input(meetingId, attendanceInputRequest);
    }

    public void edit(MeetingId meetingId, AttendanceEditRequest attendanceEditRequest) {
        attendanceRepository.edit(meetingId, attendanceEditRequest);
    }
}
