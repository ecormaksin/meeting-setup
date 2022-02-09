package meeting.infrastructure.datasource.meeting;

import meeting.application.service.meeting.MeetingRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;
import meeting.domain.model.schedule.*;
import meeting.infrastructure.datasource.attendance.AttendanceMapper;
import meeting.infrastructure.datasource.schedule.ScheduleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class MeetingDatasource implements MeetingRepository {
    MeetingMapper meetingMapper;
    ScheduleMapper scheduleMapper;
    AttendanceMapper attendanceMapper;

    public MeetingDatasource(MeetingMapper meetingMapper, ScheduleMapper scheduleMapper, AttendanceMapper attendanceMapper) {
        this.meetingMapper = meetingMapper;
        this.scheduleMapper = scheduleMapper;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingId create(MeetingCreationRequest meetingCreationRequest) {
        MeetingId meetingId = MeetingId.newId();

        insertMeeting(meetingId, meetingCreationRequest.subject());
        insertMeetingMemo(meetingId, meetingCreationRequest.memo());
        insertSchedules(meetingId, meetingCreationRequest.schedules());
        insertOrganizerTerminal(meetingId, meetingCreationRequest.organizerTerminal());

        return meetingId;
    }

    @Override
    public Meeting meetingOf(MeetingId meetingId) {
        boolean meetingNotFound = false;

        Optional<Subject> optSubject = meetingMapper.selectSubject(meetingId);
        if (!optSubject.isPresent()) {
            meetingNotFound = true;
        }

        Optional<Memo> optMemo = meetingMapper.selectMemo(meetingId);

        Optional<ClientBrowser> optOrganizerTerminal = meetingMapper.selectOrganizerTerminal(meetingId);
        if (!optOrganizerTerminal.isPresent()) {
            meetingNotFound = true;
        }

        List<Schedule> scheduleList = scheduleMapper.selectSchedulesByMeetingId(meetingId);
        if (scheduleList.isEmpty()) {
            meetingNotFound = true;
        }

        if (meetingNotFound) {
            throw new MeetingNotFoundException(String.format("存在しないミーティングです。ミーティングIDを確認してください。: %s", meetingId.value()));
        }

        List<Attendance> attendanceList = attendanceMapper.selectAttendancesByMeetingId(meetingId);

        return new Meeting(meetingId,
                optSubject.get(),
                optMemo.orElse(null),
                new Schedules(scheduleList),
                Attendances.of(attendanceList),
                optOrganizerTerminal.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Meeting meeting, MeetingEditRequest meetingEditRequest) {
        MeetingId meetingId = meetingEditRequest.meetingId();

        // 既存のレコードを削除
        deleteMeetingWithWholeAggregate(meetingId);

        // 再追加
        insertMeeting(meetingId, meetingEditRequest.subject());
        insertMeetingMemo(meetingId, meetingEditRequest.memo());
        Set<ScheduleNumber> scheduleNumbers = insertSchedules(meetingId, meetingEditRequest.schedules());
        insertOrganizerTerminal(meetingId, meetingEditRequest.organizerTerminal());
        insertAttendances(meetingId, meeting.attendances(), scheduleNumbers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(MeetingId meetingId) {
        deleteMeetingWithWholeAggregate(meetingId);
    }

    @Override
    public OrganizerMeetingList organizerMeetingList(ClientBrowser organizerTerminal) {

        List<OrganizerMeetingListItem> list = meetingMapper.organizerMeetingListItems(
                organizerTerminal.ipAddress(),
                organizerTerminal.osFamily(),
                organizerTerminal.userAgentFamily()
        );

        return OrganizerMeetingList.of(list);
    }

    @Override
    public OrganizerMeetingList search(ClientBrowser organizerTerminal, MeetingSearchCondition searchCondition) {
        return null;
    }

    private void insertMeeting(MeetingId meetingId, Subject subject) {
        meetingMapper.insertMeeting(meetingId, subject);
    }

    private void insertMeetingMemo(MeetingId meetingId, Memo memo) {
        if (memo.inputStatus() == MemoInputStatus.なし) return;
        meetingMapper.insertMeetingMemo(meetingId, memo);
    }

    private Set<ScheduleNumber> insertSchedules(MeetingId meetingId, Schedules schedules) {
        Set<ScheduleNumber> scheduleNumbers = new HashSet<>();
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.stream().forEach(schedule -> {
            if (schedule.validity() != ScheduleValidity.妥当) {
                return;
            }

            int scheduleNumber = schedule.scheduleNumberIssuedStatus() == ScheduleNumberIssuedStatus.未発番 ?
                    scheduleMapper.newScheduleNumber()
                    :
                    schedule.scheduleNumber().value();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingId,
                    schedule.startDateTime(),
                    schedule.endDateTime()
            );
            scheduleNumbers.add(new ScheduleNumber(scheduleNumber));
        });

        return scheduleNumbers;
    }

    private void insertOrganizerTerminal(MeetingId meetingId, ClientBrowser clientBrowser) {
        meetingMapper.insertOrganizerTerminal(meetingId,
                clientBrowser.ipAddress(),
                clientBrowser.osFamily(),
                clientBrowser.userAgentFamily()
        );
    }

    private void deleteMeetingWithWholeAggregate(MeetingId meetingId) {

        attendanceMapper.deleteAttendanceCommentsByMeetingId(meetingId);
        attendanceMapper.deleteConveniencesByMeetingId(meetingId);
        attendanceMapper.deleteAttendancesByMeetingId(meetingId);

        scheduleMapper.deleteSchedulesByMeetingId(meetingId);

        meetingMapper.deleteOrganizerTerminal(meetingId);
        meetingMapper.deleteMeetingMemo(meetingId);
        meetingMapper.deleteMeeting(meetingId);
    }

    private void insertAttendances(MeetingId meetingId, Attendances attendances, Set<ScheduleNumber> scheduleNumbers) {

        for (Attendance attendance : attendances.asList()) {
            AttendanceId attendanceId = attendance.attendanceId();
            attendanceMapper.insertAttendance(meetingId, attendanceId, attendance.participantName());
            insertConveniences(attendanceId, attendance.conveniences(), scheduleNumbers);

            AttendanceComment attendanceComment = attendance.attendanceComment();
            if (attendanceComment.inputStatus() == AttendanceCommentInputStatus.なし) continue;
            attendanceMapper.insertAttendanceComment(attendanceId, attendanceComment);
        }
    }

    private void insertConveniences(AttendanceId attendanceId, Conveniences conveniences, Set<ScheduleNumber> scheduleNumbers) {

        for (Convenience convenience : conveniences.asList()) {
            if (isScheduleDeleted(scheduleNumbers, convenience.scheduleNumber())) {
                continue;
            }
            int convenienceNumber = attendanceMapper.newConvenienceNumber();
            attendanceMapper.insertConvenience(convenienceNumber,
                    attendanceId,
                    convenience.scheduleNumber(),
                    convenience.convenienceCategory());
        }
    }

    private boolean isScheduleDeleted(Set<ScheduleNumber> scheduleNumbers, ScheduleNumber scheduleNumber) {
        return !scheduleNumbers.contains(scheduleNumber);
    }
}
