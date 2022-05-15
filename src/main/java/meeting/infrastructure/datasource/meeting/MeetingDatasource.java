package meeting.infrastructure.datasource.meeting;

import meeting.application.service.meeting.MeetingRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.meeting_list.MeetingList;
import meeting.domain.model.meeting.meeting_list.MeetingListItem;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;
import meeting.domain.model.schedule.*;
import meeting.infrastructure.datasource.attendance.AttendanceMapper;
import meeting.infrastructure.datasource.schedule.ScheduleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public MeetingNumber create(MeetingCreationRequest meetingCreationRequest) {
        MeetingNumber meetingNumber = MeetingNumber.newNumber();

        insertMeeting(meetingNumber, meetingCreationRequest.subject());
        insertMeetingMemo(meetingNumber, meetingCreationRequest.memo());
        insertSchedules(meetingNumber, meetingCreationRequest.schedules());
        insertOrganizerTerminal(meetingNumber, meetingCreationRequest.organizerTerminal());

        return meetingNumber;
    }

    @Override
    public Meeting meetingOf(MeetingNumber meetingNumber) {
        boolean meetingNotFound = false;

        Optional<Subject> optSubject = meetingMapper.selectSubject(meetingNumber);
        if (optSubject.isEmpty()) {
            meetingNotFound = true;
        }

        Optional<Memo> optMemo = meetingMapper.selectMemo(meetingNumber);

        Optional<ClientBrowser> optOrganizerTerminal = meetingMapper.selectOrganizerTerminal(meetingNumber);
        if (optOrganizerTerminal.isEmpty()) {
            meetingNotFound = true;
        }

        List<Schedule> scheduleList = scheduleMapper.selectSchedulesByMeetingId(meetingNumber);
        if (scheduleList.isEmpty()) {
            meetingNotFound = true;
        }

        if (meetingNotFound) {
            throw new MeetingNotFoundException(String.format("存在しないミーティングです。ミーティング番号を確認してください。: %s", meetingNumber.value()));
        }

        List<Attendance> attendanceList = attendanceMapper.selectAttendancesByMeetingId(meetingNumber);

        return new Meeting(meetingNumber,
                optSubject.get(),
                optMemo.orElse(null),
                new Schedules(scheduleList),
                Attendances.of(attendanceList),
                optOrganizerTerminal.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Meeting meeting, MeetingEditRequest meetingEditRequest) {
        MeetingNumber meetingNumber = meetingEditRequest.meetingId();

        // 既存のレコードを削除
        deleteMeetingWithWholeAggregate(meetingNumber);

        // 再追加
        insertMeeting(meetingNumber, meetingEditRequest.subject());
        insertMeetingMemo(meetingNumber, meetingEditRequest.memo());
        Set<ScheduleNumber> scheduleNumbers = insertSchedules(meetingNumber, meetingEditRequest.schedules());
        insertOrganizerTerminal(meetingNumber, meetingEditRequest.organizerTerminal());
        insertAttendances(meetingNumber, meeting.attendances(), scheduleNumbers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(MeetingNumber meetingNumber) {
        deleteMeetingWithWholeAggregate(meetingNumber);
    }

    @Override
    public MeetingList search(MeetingSearchRequest meetingSearchRequest) {
        ClientBrowser organizerTerminal = meetingSearchRequest.organizerTerminal();
        MeetingSearchCondition searchCondition = meetingSearchRequest.searchCondition();

        LocalDateTime startDateTime = LocalDateTime.of(searchCondition.startDate().value(), LocalTime.of(0,0,0));
        LocalDateTime endDateTime = LocalDateTime.of(searchCondition.endDate().value(), LocalTime.of(23, 59, 59));

        List<MeetingListItem> list = meetingMapper.search(
                organizerTerminal.ipAddress(),
                organizerTerminal.osFamily(),
                organizerTerminal.userAgentFamily(),
                searchCondition.keyword(),
                startDateTime,
                endDateTime);

        return MeetingList.of(list);
    }

    private void insertMeeting(MeetingNumber meetingNumber, Subject subject) {
        meetingMapper.insertMeeting(meetingNumber, subject);
    }

    private void insertMeetingMemo(MeetingNumber meetingNumber, Memo memo) {
        if (memo.isBlank()) return;
        meetingMapper.insertMeetingMemo(meetingNumber, memo);
    }

    private Set<ScheduleNumber> insertSchedules(MeetingNumber meetingNumber, Schedules schedules) {
        Set<ScheduleNumber> scheduleNumbers = new HashSet<>();
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.forEach(schedule -> {

            ScheduleNumber scheduleNumber = schedule.scheduleNumberIssued() ?
                    new ScheduleNumber(scheduleMapper.newScheduleNumber())
                    :
                    schedule.scheduleNumber();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingNumber,
                    schedule.meetingDate(),
                    String.valueOf(schedule.startHourValue()),
                    schedule.startMinuteLabel(),
                    String.valueOf(schedule.durationValue())
            );
            scheduleNumbers.add(scheduleNumber);
        });

        return scheduleNumbers;
    }

    private void insertOrganizerTerminal(MeetingNumber meetingNumber, ClientBrowser clientBrowser) {
        meetingMapper.insertOrganizerTerminal(meetingNumber,
                clientBrowser.ipAddress(),
                clientBrowser.osFamily(),
                clientBrowser.userAgentFamily()
        );
    }

    private void deleteMeetingWithWholeAggregate(MeetingNumber meetingNumber) {

        attendanceMapper.deleteAttendanceCommentsByMeetingId(meetingNumber);
        attendanceMapper.deleteConveniencesByMeetingId(meetingNumber);
        attendanceMapper.deleteAttendancesByMeetingId(meetingNumber);

        scheduleMapper.deleteSchedulesByMeetingId(meetingNumber);

        meetingMapper.deleteOrganizerTerminal(meetingNumber);
        meetingMapper.deleteMeetingMemo(meetingNumber);
        meetingMapper.deleteMeeting(meetingNumber);
    }

    private void insertAttendances(MeetingNumber meetingNumber, Attendances attendances, Set<ScheduleNumber> scheduleNumbers) {

        for (Attendance attendance : attendances.asList()) {
            AttendanceId attendanceId = attendance.attendanceId();
            attendanceMapper.insertAttendance(meetingNumber, attendanceId, attendance.participantName());
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
