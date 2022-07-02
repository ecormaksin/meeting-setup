package meeting.infrastructure.datasource.meeting;

import meeting.application.service.meeting.MeetingRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.meeting_list.MeetingList;
import meeting.domain.model.meeting.meeting_list.MeetingListItem;
import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.ScheduleRegistrationStatus;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.infrastructure.datasource.attendance.AttendanceMapper;
import meeting.infrastructure.datasource.schedule.ScheduleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
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
    public void create(MeetingCreationRequest meetingCreationRequest) {

        final MeetingNumber meetingNumber = meetingCreationRequest.meetingNumber();

        insertMeeting(meetingNumber,
                meetingCreationRequest.subject(),
                meetingCreationRequest.organizerNumber());
        insertMeetingMemo(meetingNumber, meetingCreationRequest.memo());
        insertSchedules(meetingNumber, meetingCreationRequest.schedules());
    }

    @Override
    public Meeting get(MeetingNumber meetingNumber) {
        boolean meetingNotFound = false;

        final Optional<MeetingWithoutSchedulesAndAttendances> optionalMeetingWithoutSchedulesAndAttendances = meetingMapper.selectMeeting(meetingNumber);
        if (optionalMeetingWithoutSchedulesAndAttendances.isEmpty()) {
            meetingNotFound = true;
        }

        final List<Schedule> scheduleList = scheduleMapper.selectSchedulesByMeetingNumber(meetingNumber);
        if (scheduleList.isEmpty()) {
            meetingNotFound = true;
        }

        if (meetingNotFound) {
            throw new MeetingNotFoundException(String.format("存在しないミーティングです。ミーティング番号を確認してください。: %s", meetingNumber.value()));
        }

        final List<Attendance> attendanceList = attendanceMapper.selectAttendancesByMeetingNumber(meetingNumber);

        final MeetingWithoutSchedulesAndAttendances meetingResource = optionalMeetingWithoutSchedulesAndAttendances.get();

        return new Meeting(meetingNumber,
                meetingResource.subject(),
                meetingResource.memo(),
                meetingResource.organizerNumber(),
                new Schedules(scheduleList),
                new Attendances(attendanceList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Meeting meeting, MeetingEditRequest meetingEditRequest) {
        MeetingNumber meetingNumber = meetingEditRequest.meetingNumber();

        // 既存のレコードを削除
        deleteMeetingWithWholeAggregate(meetingNumber);

        // 再追加
        insertMeeting(meetingNumber,
                meetingEditRequest.subject(),
                meetingEditRequest.organizerNumber());
        insertMeetingMemo(meetingNumber, meetingEditRequest.memo());
        Set<ScheduleNumber> scheduleNumbers = insertSchedules(meetingNumber, meetingEditRequest.schedules());
        insertAttendances(meetingNumber, meeting.attendances(), scheduleNumbers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(MeetingNumber meetingNumber) {
        deleteMeetingWithWholeAggregate(meetingNumber);
    }

    @Override
    public MeetingList search(MeetingSearchRequest meetingSearchRequest) {
        OrganizerNumber organizerNumber = meetingSearchRequest.organizerNumber();
        MeetingSearchCondition searchCondition = meetingSearchRequest.searchCondition();

        LocalDateTime startDateTime = LocalDateTime.of(searchCondition.startDate().value(), LocalTime.of(0,0,0));
        LocalDateTime endDateTime = LocalDateTime.of(searchCondition.endDate().value(), LocalTime.of(23, 59, 59));

        List<MeetingListItem> list = meetingMapper.search(
                organizerNumber,
                searchCondition.keyword(),
                startDateTime,
                endDateTime);

        return MeetingList.of(list);
    }

    private void insertMeeting(MeetingNumber meetingNumber, Subject subject, OrganizerNumber organizerNumber) {
        meetingMapper.insertMeeting(meetingNumber, subject, organizerNumber);
    }

    private void insertMeetingMemo(MeetingNumber meetingNumber, Memo memo) {
        if (memo.isBlank()) return;
        meetingMapper.insertMeetingMemo(meetingNumber, memo);
    }

    private Set<ScheduleNumber> insertSchedules(MeetingNumber meetingNumber, Schedules schedules) {
        Set<ScheduleNumber> scheduleNumbers = new HashSet<>();
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.forEach(schedule -> {

            ScheduleNumber scheduleNumber = schedule.registrationStatus() == ScheduleRegistrationStatus.追加 ?
                    new ScheduleNumber(scheduleMapper.newScheduleNumber())
                    :
                    schedule.scheduleNumber();
            final ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingNumber,
                    scheduleConstruct.meetingDate(),
                    scheduleConstruct.startHour(),
                    scheduleConstruct.startMinute(),
                    scheduleConstruct.duration());
            scheduleNumbers.add(scheduleNumber);
        });

        return scheduleNumbers;
    }

    private void deleteMeetingWithWholeAggregate(MeetingNumber meetingNumber) {

        attendanceMapper.deleteAttendanceCommentsByMeetingNumber(meetingNumber);
        attendanceMapper.deleteConveniencesByMeetingNumber(meetingNumber);
        attendanceMapper.deleteAttendancesByMeetingNumber(meetingNumber);

        scheduleMapper.deleteSchedulesByMeetingNumber(meetingNumber);

        meetingMapper.deleteMeetingMemo(meetingNumber);
        meetingMapper.deleteMeeting(meetingNumber);
    }

    private void insertAttendances(MeetingNumber meetingNumber, Attendances attendances, Set<ScheduleNumber> scheduleNumbers) {

        for (Attendance attendance : attendances.asList()) {
            AttendanceNumber attendanceNumber = attendance.attendanceNumber();
            attendanceMapper.insertAttendance(meetingNumber, attendanceNumber, attendance.participantName());
            insertConveniences(attendanceNumber, attendance.conveniences(), scheduleNumbers);

            AttendanceComment attendanceComment = attendance.attendanceComment();
            if (attendanceComment.inputStatus() == AttendanceCommentInputStatus.なし) continue;
            attendanceMapper.insertAttendanceComment(attendanceNumber, attendanceComment);
        }
    }

    private void insertConveniences(AttendanceNumber attendanceNumber, Conveniences conveniences, Set<ScheduleNumber> scheduleNumbers) {

        for (Convenience convenience : conveniences.asList()) {
            if (isScheduleDeleted(scheduleNumbers, convenience.scheduleNumber())) {
                continue;
            }
            int convenienceNumber = attendanceMapper.newConvenienceNumber();
            attendanceMapper.insertConvenience(convenienceNumber,
                    attendanceNumber,
                    convenience.scheduleNumber(),
                    convenience.convenienceCategory());
        }
    }

    private boolean isScheduleDeleted(Set<ScheduleNumber> scheduleNumbers, ScheduleNumber scheduleNumber) {
        return !scheduleNumbers.contains(scheduleNumber);
    }
}
