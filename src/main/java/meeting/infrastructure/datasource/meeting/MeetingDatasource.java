package meeting.infrastructure.datasource.meeting;

import meeting.application.service.meeting.MeetingRepository;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.list.MeetingList;
import meeting.domain.model.meeting.list.MeetingListItem;
import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.ScheduleRegistrationStatus;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.infrastructure.datasource.attendance.AttendanceMapper;
import meeting.infrastructure.datasource.organizer.OrganizerMapper;
import meeting.infrastructure.datasource.schedule.ScheduleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class MeetingDatasource implements MeetingRepository {

    OrganizerMapper organizerMapper;
    MeetingMapper meetingMapper;
    ScheduleMapper scheduleMapper;
    AttendanceMapper attendanceMapper;

    public MeetingDatasource(
            OrganizerMapper organizerMapper,
            MeetingMapper meetingMapper,
            ScheduleMapper scheduleMapper,
            AttendanceMapper attendanceMapper) {

        this.organizerMapper = organizerMapper;
        this.meetingMapper = meetingMapper;
        this.scheduleMapper = scheduleMapper;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MeetingRegistrationRequest registrationRequest) {

        OrganizerNumber organizerNumber = registrationRequest.organizerNumber();
        MeetingNumber meetingNumber = registrationRequest.meetingNumber();

        if (organizerMapper.notExists(organizerNumber)) organizerMapper.insertOrganizer(organizerNumber);

        insertMeeting(meetingNumber, registrationRequest.subject());
        insertMeetingAndOrganizerRelation(meetingNumber, organizerNumber);
        insertMeetingMemo(meetingNumber, registrationRequest.memo());
        insertSchedules(meetingNumber, registrationRequest.schedules());
    }

    @Override
    public Meeting get(MeetingNumber meetingNumber) {
        boolean meetingNotFound = false;

        Optional<MeetingWithoutSchedulesAndAttendances> optionalMeetingWithoutSchedulesAndAttendances = meetingMapper.selectMeeting(meetingNumber);
        if (optionalMeetingWithoutSchedulesAndAttendances.isEmpty()) {
            meetingNotFound = true;
        }

        List<Schedule> scheduleList = scheduleMapper.selectSchedulesByMeetingNumber(meetingNumber);
        if (scheduleList.isEmpty()) {
            meetingNotFound = true;
        }

        if (meetingNotFound) {
            throw new MeetingNotFoundException(meetingNumber);
        }

        List<Attendance> attendanceList = attendanceMapper.selectAttendancesByMeetingNumber(meetingNumber);

        MeetingWithoutSchedulesAndAttendances meetingResource = optionalMeetingWithoutSchedulesAndAttendances.get();

        return new Meeting(meetingNumber,
                meetingResource.subject(),
                meetingResource.memo(),
                meetingResource.organizerNumber(),
                Schedules.of(scheduleList),
                Attendances.of(attendanceList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(Meeting meeting, MeetingRegistrationRequest registrationRequest) {
        MeetingNumber meetingNumber = registrationRequest.meetingNumber();

        // 既存のレコードを削除
        deleteMeetingWithWholeAggregate(meetingNumber);

        // 再追加
        insertMeeting(meetingNumber,
                registrationRequest.subject());
        insertMeetingMemo(meetingNumber, registrationRequest.memo());
        insertMeetingAndOrganizerRelation(meetingNumber, registrationRequest.organizerNumber());
        insertSchedules(meetingNumber, registrationRequest.schedules());
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

        List<MeetingListItem> list = meetingMapper.search(
                organizerNumber,
                searchCondition.keyword(),
                searchCondition.startDate(),
                searchCondition.endDate(),
                searchCondition.limitCount()
        );

        return MeetingList.of(list);
    }

    private void insertMeeting(MeetingNumber meetingNumber, Subject subject) {
        meetingMapper.insertMeeting(meetingNumber, subject);
    }

    private void insertMeetingMemo(MeetingNumber meetingNumber, Memo memo) {
        if (memo.isBlank()) return;
        meetingMapper.insertMeetingMemo(meetingNumber, memo);
    }

    private void insertSchedules(MeetingNumber meetingNumber, Schedules schedules) {
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.forEach(schedule -> {

            ScheduleNumber scheduleNumber = schedule.registrationStatus() == ScheduleRegistrationStatus.追加 ?
                    new ScheduleNumber(scheduleMapper.newScheduleNumber())
                    :
                    schedule.scheduleNumber();
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingNumber,
                    scheduleConstruct.meetingDate(),
                    scheduleConstruct.startHour(),
                    scheduleConstruct.startMinute(),
                    scheduleConstruct.duration());
        });
    }

    private void insertMeetingAndOrganizerRelation(MeetingNumber meetingNumber,
                                                   OrganizerNumber organizerNumber) {
        organizerMapper.insertMeetingAndOrganizerRelation(meetingNumber, organizerNumber);
    }

    private void deleteMeetingWithWholeAggregate(MeetingNumber meetingNumber) {

        scheduleMapper.deleteSchedulesByMeetingNumber(meetingNumber);

        organizerMapper.deleteMeetingAndOrganizerRelation(meetingNumber);

        meetingMapper.deleteMeetingMemo(meetingNumber);
        meetingMapper.deleteMeeting(meetingNumber);
    }
}
