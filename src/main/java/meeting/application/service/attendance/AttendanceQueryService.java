package meeting.application.service.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.ParticipantName;
import meeting.domain.model.attendance.ParticipantNameDuplicationStatus;
import meeting.domain.model.meeting.MeetingId;
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
            MeetingId meetingId,
            ParticipantName participantName) {

        return attendanceRepository.participantNameDuplicationStatus(meetingId, participantName);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingId meetingId,
            AttendanceId attendanceId,
            ParticipantName participantName) {

        return attendanceRepository.participantNameDuplicationStatus(meetingId, attendanceId, participantName);
    }
}
