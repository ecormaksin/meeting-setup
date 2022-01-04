package meeting.infrastructure.datasource.attendance;

import meeting.application.service.attendance.AttendanceRepository;
import meeting.domain.model.attendance.AttendanceComment;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.attendance.ParticipantName;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.MeetingId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AttendanceDatasource implements AttendanceRepository {
    AttendanceMapper attendanceMapper;

    public AttendanceDatasource (AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttendanceId input(MeetingId meetingId, AttendanceInputRequest attendanceInputRequest) {
        AttendanceId attendanceId = AttendanceId.newId();

        insertAttendance(meetingId, attendanceId, attendanceInputRequest.participantName());
        insertConveniences(attendanceId, attendanceInputRequest.conveniences());
        insertAttendanceComment(attendanceId, attendanceInputRequest.attendanceComment());

        return attendanceId;
    }

    private void insertAttendance(MeetingId meetingId, AttendanceId attendanceId, ParticipantName participantName) {
        attendanceMapper.insertAttendance(meetingId, attendanceId, participantName);
    }

    private void insertConveniences(AttendanceId attendanceId, Conveniences conveniences) {
        List<Convenience> convenienceList = conveniences.asList();
        convenienceList.stream().forEach(convenience -> {
            int convenienceNumber = attendanceMapper.newConvenienceNumber();
            attendanceMapper.insertConvenience(convenienceNumber,
                    attendanceId,
                    convenience.scheduleNumber(),
                    convenience.convenienceCategory());
        });
    }

    private void insertAttendanceComment(AttendanceId attendanceId, AttendanceComment attendanceComment) {
        if (attendanceComment.isBlank()) return;
        attendanceMapper.insertAttendanceComment(attendanceId, attendanceComment);
    }
}
