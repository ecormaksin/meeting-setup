package example.datasource.meeting;

import example.application.service.meeting.MeetingRepository;
import example.datasource.schedule.ScheduleMapper;
import example.domain.model.meeting.MeetingCreationRequest;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.Memo;
import example.domain.model.meeting.Subject;
import example.domain.model.schedule.Schedule;
import example.domain.model.schedule.Schedules;
import example.domain.model.schedule.construct.EndTime;
import example.domain.model.schedule.construct.MeetingDate;
import example.domain.model.schedule.construct.ScheduleConstruct;
import example.domain.model.schedule.construct.StartTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MeetingDatasource implements MeetingRepository {
    MeetingMapper meetingMapper;
    ScheduleMapper scheduleMapper;

    public MeetingDatasource(MeetingMapper meetingMapper, ScheduleMapper scheduleMapper) {
        this.meetingMapper = meetingMapper;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingId create(MeetingCreationRequest meetingCreationRequest) {
        MeetingId meetingId = MeetingId.newId();

        insertMeeting(meetingId, meetingCreationRequest.subject());
        insertMeetingMemo(meetingId, meetingCreationRequest.memo());
        insertSchedules(meetingId, meetingCreationRequest.schedules());

        return meetingId;
    }

    private void insertMeeting(MeetingId meetingId, Subject subject) {
        meetingMapper.insertMeeting(meetingId, subject);
    }

    private void insertMeetingMemo(MeetingId meetingId, Memo memo) {
        if (memo.isBlank()) return;
        meetingMapper.insertMeetingMemo(meetingId, memo);
    }

    private void insertSchedules(MeetingId meetingId, Schedules schedules) {
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.stream().forEach(schedule -> {
            int scheduleNumber = scheduleMapper.newScheduleNumber();
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            MeetingDate meetingDate = scheduleConstruct.meetingDate();
            StartTime startTime = scheduleConstruct.startTime();
            EndTime endTime = scheduleConstruct.endTime();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingId,
                    LocalDateTime.of(meetingDate.value(), startTime.value()),
                    LocalDateTime.of(meetingDate.value(), endTime.value())
            );
        });
    }
}
