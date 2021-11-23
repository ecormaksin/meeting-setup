package example.datasource.meeting;

import example.application.service.meeting.MeetingRepository;
import example.datasource.schedule.ScheduleMapper;
import example.domain.model.meeting.MeetingCreationRequest;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.Memo;
import example.domain.model.meeting.Subject;
import example.domain.model.meeting.organizer.OrganizerTerminal;
import example.domain.model.schedule.Schedule;
import example.domain.model.schedule.Schedules;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        insertOrganizerTerminal(meetingId, meetingCreationRequest.organizerTerminal());

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
            if (!schedule.isValid()) {
                return;
            }

            int scheduleNumber = scheduleMapper.newScheduleNumber();
            scheduleMapper.insertSchedule(
                    scheduleNumber,
                    meetingId,
                    schedule.startDateTime(),
                    schedule.endDateTime()
            );
        });
    }

    private void insertOrganizerTerminal(MeetingId meetingId, OrganizerTerminal organizerTerminal) {
        meetingMapper.insertOrganizerTerminal(meetingId,
                organizerTerminal.ipAddress(),
                organizerTerminal.osFamily(),
                organizerTerminal.userAgentFamily()
        );
    }
}
