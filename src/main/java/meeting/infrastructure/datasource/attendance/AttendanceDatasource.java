package meeting.infrastructure.datasource.attendance;

import meeting.application.service.attendance.AttendanceRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.infrastructure.datasource.meeting.MeetingMapper;
import meeting.infrastructure.datasource.meeting.MeetingNotFoundException;
import meeting.infrastructure.datasource.meeting.MeetingWithoutSchedulesAndAttendances;
import meeting.infrastructure.datasource.participant.ParticipantMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceDatasource implements AttendanceRepository {

    MeetingMapper meetingMapper;
    ParticipantMapper participantMapper;
    AttendanceMapper attendanceMapper;

    public AttendanceDatasource(
            MeetingMapper meetingMapper,
            ParticipantMapper participantMapper,
            AttendanceMapper attendanceMapper) {

        this.meetingMapper = meetingMapper;
        this.participantMapper = participantMapper;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    public AttendanceInputDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            ParticipantNumber participantNumber) {

        return participantNumberDuplicationStatus(meetingNumber, null, participantNumber);
    }

    @Override
    public AttendanceInputDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber) {

        boolean duplicated = attendanceMapper.participantNumberDuplicated(meetingNumber, attendanceNumber, participantNumber);
        return duplicated ? AttendanceInputDuplicationStatus.あり : AttendanceInputDuplicationStatus.なし;
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
    public void input(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest) {

        Optional<MeetingWithoutSchedulesAndAttendances> optionalMeetingWithoutSchedulesAndAttendances = meetingMapper.selectMeeting(meetingNumber);
        if (optionalMeetingWithoutSchedulesAndAttendances.isEmpty()) {
            throw new MeetingNotFoundException(meetingNumber);
        }

        AttendanceNumber attendanceNumber = registrationRequest.attendanceNumber();
        ParticipantNumber participantNumber = registrationRequest.participantNumber();
        ParticipantName participantName = registrationRequest.participantName();

        if (participantMapper.notExists(participantNumber)) participantMapper.insertParticipant(participantNumber);

        updateLatestParticipantName(participantNumber, participantName);

        insertAttendance(meetingNumber, attendanceNumber, participantNumber, registrationRequest.participantName());
        insertConveniences(attendanceNumber, registrationRequest.conveniences());
        insertAttendanceComment(attendanceNumber, registrationRequest.attendanceComment());
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
    public void edit(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest) {

        Optional<MeetingWithoutSchedulesAndAttendances> optionalMeetingWithoutSchedulesAndAttendances = meetingMapper.selectMeeting(meetingNumber);
        if (optionalMeetingWithoutSchedulesAndAttendances.isEmpty()) {
            throw new MeetingNotFoundException(meetingNumber);
        }

        AttendanceNumber attendanceNumber = registrationRequest.attendanceNumber();
        ParticipantNumber participantNumber = registrationRequest.participantNumber();
        ParticipantName participantName = registrationRequest.participantName();

        updateLatestParticipantName(participantNumber, participantName);

        // 既存のレコードを削除
        deleteAttendanceWithWholeAggregate(attendanceNumber);

        // 再追加
        insertAttendance(meetingNumber, attendanceNumber, participantNumber, registrationRequest.participantName());
        insertConveniences(attendanceNumber, registrationRequest.conveniences());
        insertAttendanceComment(attendanceNumber, registrationRequest.attendanceComment());
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
