package example.datasource.meeting;

import example.application.service.meeting.MeetingRepository;
import example.datasource.schedule.ScheduleMapper;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingSetupRequest;
import example.domain.model.meeting.Memo;
import example.domain.model.schedule.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    public MeetingId setup(MeetingSetupRequest meetingSetupRequest) {
        MeetingId meetingId = MeetingId.newId();

        meetingMapper.insertMeeting(
                meetingId,
                meetingSetupRequest.subject());

        Memo memo = meetingSetupRequest.memo();
        if (memo.isNotBlank()) {
            meetingMapper.insertMeetingMemo(
                    meetingId,
                    meetingSetupRequest.memo());
        }

        Schedules schedules = meetingSetupRequest.schedules();
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.stream().forEach(schedule -> {
            int scheduleNumber = scheduleMapper.newScheduleNumber();
            MeetingDate meetingDate = schedule.meetingDate();
            StartTime startTime = schedule.startTime();
            EndTime endTime = schedule.endTime();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingId,
                    LocalDateTime.of(meetingDate.value(), startTime.value()),
                    LocalDateTime.of(meetingDate.value(), endTime.value())
            );
        });

        return meetingId;
    }
}
