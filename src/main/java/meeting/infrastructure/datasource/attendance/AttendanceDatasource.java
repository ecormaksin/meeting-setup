package meeting.infrastructure.datasource.attendance;

import meeting.application.service.attendance.AttendanceRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static meeting.domain.model.participant.ParticipantNameDuplicationStatus.あり;
import static meeting.domain.model.participant.ParticipantNameDuplicationStatus.なし;

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
            AttendanceNumber attendanceNumber,
            ParticipantName participantName) {

        boolean duplicated = attendanceMapper.participantNameDuplicated(meetingNumber, attendanceNumber, participantName);
        if (duplicated) return あり;
        return なし;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttendanceNumber input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        AttendanceNumber attendanceNumber = AttendanceNumber.newNumber();

        insertAttendance(meetingNumber, attendanceNumber, attendanceInputRequest.participantName());
        insertConveniences(attendanceNumber, attendanceInputRequest.conveniences());
        insertAttendanceComment(attendanceNumber, attendanceInputRequest.attendanceComment());

        return attendanceNumber;
    }

    @Override
    public Attendance attendanceOf(AttendanceNumber attendanceNumber) {
        return attendanceMapper.selectAttendance(attendanceNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest) {
        AttendanceNumber attendanceNumber = attendanceEditRequest.attendanceId();

        // 既存のレコードを削除
        deleteAttendanceWithWholeAggregate(attendanceNumber);

        // 再追加
        insertAttendance(meetingNumber, attendanceNumber, attendanceEditRequest.participantName());
        insertConveniences(attendanceNumber, attendanceEditRequest.conveniences());
        insertAttendanceComment(attendanceNumber, attendanceEditRequest.attendanceComment());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AttendanceNumber attendanceNumber) {
        deleteAttendanceWithWholeAggregate(attendanceNumber);
    }

    private void insertAttendance(MeetingNumber meetingNumber, AttendanceNumber attendanceNumber, ParticipantName participantName) {
        attendanceMapper.insertAttendance(meetingNumber, attendanceNumber, participantName);
    }

    private void insertConveniences(AttendanceNumber attendanceNumber, Conveniences conveniences) {
        List<Convenience> convenienceList = conveniences.asList();
        convenienceList.stream().forEach(convenience -> {
            int convenienceNumber = attendanceMapper.newConvenienceNumber();
            attendanceMapper.insertConvenience(convenienceNumber,
                    attendanceNumber,
                    convenience.scheduleNumber(),
                    convenience.convenienceCategory());
        });
    }

    private void insertAttendanceComment(AttendanceNumber attendanceNumber, AttendanceComment attendanceComment) {
        if (attendanceComment.inputStatus() == AttendanceCommentInputStatus.なし) return;
        attendanceMapper.insertAttendanceComment(attendanceNumber, attendanceComment);
    }

    private void deleteAttendanceWithWholeAggregate(AttendanceNumber attendanceNumber) {
        attendanceMapper.deleteAttendanceComment(attendanceNumber);
        attendanceMapper.deleteConveniences(attendanceNumber);
        attendanceMapper.deleteAttendance(attendanceNumber);
    }
}
