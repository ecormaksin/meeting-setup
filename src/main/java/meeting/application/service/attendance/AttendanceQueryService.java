package meeting.application.service.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.ParticipantName;
import meeting.domain.model.attendance.ParticipantNameDuplicationStatus;
import meeting.domain.model.meeting.MeetingNumber;
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

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantName participantName) {

        return attendanceRepository.participantNameDuplicationStatus(meetingNumber, participantName);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceId attendanceId,
            ParticipantName participantName) {

        return attendanceRepository.participantNameDuplicationStatus(meetingNumber, attendanceId, participantName);
    }
}
