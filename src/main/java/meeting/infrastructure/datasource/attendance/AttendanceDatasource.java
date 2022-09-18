package meeting.infrastructure.datasource.attendance;

import meeting.application.service.attendance.AttendanceRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberDuplicationStatus;
import meeting.infrastructure.datasource.participant.ParticipantMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AttendanceDatasource implements AttendanceRepository {

    private final ParticipantMapper participantMapper;
    private final AttendanceMapper attendanceMapper;

    public AttendanceDatasource(
            ParticipantMapper participantMapper,
            AttendanceMapper attendanceMapper) {
        this.participantMapper = participantMapper;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public ParticipantNumberDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantNumber participantNumber) {

        return participantNumberDuplicationStatus(meetingNumber, null, participantNumber);
    }

    @Override
    public ParticipantNumberDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber) {

        boolean duplicated = attendanceMapper.participantNumberDuplicated(meetingNumber, attendanceNumber, participantNumber);
        return duplicated ? ParticipantNumberDuplicationStatus.あり : ParticipantNumberDuplicationStatus.なし;
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
        return duplicated ? ParticipantNameDuplicationStatus.あり : ParticipantNameDuplicationStatus.なし;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        final AttendanceNumber attendanceNumber = attendanceInputRequest.attendanceNumber();
        final ParticipantNumber participantNumber = attendanceInputRequest.participantNumber();
        final ParticipantName participantName = attendanceInputRequest.participantName();

        if (participantMapper.notExists(participantNumber)) participantMapper.insertParticipant(participantNumber);

        updateLatestParticipantName(participantNumber, participantName);

        insertAttendance(meetingNumber, attendanceNumber, participantNumber, attendanceInputRequest.participantName());
        insertConveniences(attendanceNumber, attendanceInputRequest.conveniences());
        insertAttendanceComment(attendanceNumber, attendanceInputRequest.attendanceComment());
    }

    void updateLatestParticipantName(ParticipantNumber participantNumber, ParticipantName participantName) {

        participantMapper.deleteLatestParticipantName(participantNumber);
        participantMapper.insertLatestParticipantName(participantNumber, participantName);
    }

    @Override
    public Attendance attendanceOf(AttendanceNumber attendanceNumber) {
        return attendanceMapper.selectAttendance(attendanceNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEditRequest) {
        final AttendanceNumber attendanceNumber = attendanceEditRequest.attendanceNumber();
        final ParticipantNumber participantNumber = attendanceEditRequest.participantNumber();
        final ParticipantName participantName = attendanceEditRequest.participantName();

        updateLatestParticipantName(participantNumber, participantName);

        // 既存のレコードを削除
        deleteAttendanceWithWholeAggregate(attendanceNumber);

        // 再追加
        insertAttendance(meetingNumber, attendanceNumber, participantNumber, attendanceEditRequest.participantName());
        insertConveniences(attendanceNumber, attendanceEditRequest.conveniences());
        insertAttendanceComment(attendanceNumber, attendanceEditRequest.attendanceComment());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AttendanceNumber attendanceNumber) {
        deleteAttendanceWithWholeAggregate(attendanceNumber);
    }

    private void insertAttendance(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber,
            ParticipantName participantName) {
        attendanceMapper.insertAttendance(meetingNumber, attendanceNumber, participantNumber, participantName);
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
