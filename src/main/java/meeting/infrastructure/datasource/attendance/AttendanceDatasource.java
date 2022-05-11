package meeting.infrastructure.datasource.attendance;

import meeting.application.service.attendance.AttendanceRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.MeetingNumber;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static meeting.domain.model.attendance.ParticipantNameDuplicationStatus.あり;
import static meeting.domain.model.attendance.ParticipantNameDuplicationStatus.なし;

@Repository
public class AttendanceDatasource implements AttendanceRepository {
    AttendanceMapper attendanceMapper;

    public AttendanceDatasource (AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantName participantName) {

        return participantNameDuplicationStatus(meetingNumber, null, participantName);
    }

    @Override
    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceId attendanceId,
            ParticipantName participantName) {

        boolean duplicated = attendanceMapper.participantNameDuplicated(meetingNumber, attendanceId, participantName);
        if (duplicated) return あり;
        return なし;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttendanceId input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        AttendanceId attendanceId = AttendanceId.newId();

        insertAttendance(meetingNumber, attendanceId, attendanceInputRequest.participantName());
        insertConveniences(attendanceId, attendanceInputRequest.conveniences());
        insertAttendanceComment(attendanceId, attendanceInputRequest.attendanceComment());

        return attendanceId;
    }

    @Override
    public Attendance attendanceOf(AttendanceId attendanceId) {
        return attendanceMapper.selectAttendance(attendanceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest) {
        AttendanceId attendanceId = attendanceEditRequest.attendanceId();

        // 既存のレコードを削除
        deleteAttendanceWithWholeAggregate(attendanceId);

        // 再追加
        insertAttendance(meetingNumber, attendanceId, attendanceEditRequest.participantName());
        insertConveniences(attendanceId, attendanceEditRequest.conveniences());
        insertAttendanceComment(attendanceId, attendanceEditRequest.attendanceComment());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AttendanceId attendanceId) {
        deleteAttendanceWithWholeAggregate(attendanceId);
    }

    private void insertAttendance(MeetingNumber meetingNumber, AttendanceId attendanceId, ParticipantName participantName) {
        attendanceMapper.insertAttendance(meetingNumber, attendanceId, participantName);
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
        if (attendanceComment.inputStatus() == AttendanceCommentInputStatus.なし) return;
        attendanceMapper.insertAttendanceComment(attendanceId, attendanceComment);
    }

    private void deleteAttendanceWithWholeAggregate(AttendanceId attendanceId) {
        attendanceMapper.deleteAttendanceComment(attendanceId);
        attendanceMapper.deleteConveniences(attendanceId);
        attendanceMapper.deleteAttendance(attendanceId);
    }
}
