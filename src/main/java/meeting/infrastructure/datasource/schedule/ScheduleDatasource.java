package meeting.infrastructure.datasource.schedule;

import meeting.application.service.schedule.ScheduleRepository;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleDatasource implements ScheduleRepository {
    ScheduleMapper scheduleMapper;

    public ScheduleDatasource(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public Schedules findByMeeingId(MeetingId meetingId) {
        List<Schedule> scheduleList = scheduleMapper.selectByMeetingId(meetingId);
        if (scheduleList.isEmpty()) {
            throw new IllegalArgumentException(String.format("ミーティングの日程候補が見つかりませんでした。主催者に次のミーティングIDを確認してください。: %s", meetingId.value()));
        }

        return new Schedules(scheduleList);
    }
}
