package meeting.infrastructure.datasource.meeting;

import meeting.application.service.meeting.MeetingRepository;
import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import meeting.infrastructure.datasource.schedule.ScheduleMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (Objects.isNull(memo)) return;
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

    @Override
    public Meeting meetingOf(MeetingId meetingId) {
        boolean meetingNotFound = false;

        Optional<Subject> optSubject = meetingMapper.selectSubject(meetingId);
        if (!optSubject.isPresent()) {
            meetingNotFound = true;
        }

        Optional<Memo> optMemo = meetingMapper.selectMemo(meetingId);

        Optional<OrganizerTerminal> optOrganizerTerminal = meetingMapper.selectOrganizerTerminal(meetingId);
        if (!optOrganizerTerminal.isPresent()) {
            meetingNotFound = true;
        }

        List<Schedule> scheduleList = scheduleMapper.selectByMeetingId(meetingId);
        if (scheduleList.isEmpty()) {
            meetingNotFound = true;
        }

        if (meetingNotFound) {
            throw new IllegalArgumentException(String.format("存在しないミーティングです。ミーティングIDを確認してください。: %s", meetingId.value()));
        }

        return new Meeting(meetingId,
                optSubject.get(),
                optMemo.orElse(null),
                new Schedules(scheduleList),
                null,
                optOrganizerTerminal.get());
    }
}
