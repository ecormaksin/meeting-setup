package meeting.application.service.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
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

    public Attendance attendanceOf(AttendanceNumber attendanceNumber) {
        return attendanceRepository.attendanceOf(attendanceNumber);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantName participantName) {

        return attendanceRepository.participantNameDuplicationStatus(meetingNumber, participantName);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantName participantName) {

        return attendanceRepository.participantNameDuplicationStatus(meetingNumber, attendanceNumber, participantName);
    }
}
