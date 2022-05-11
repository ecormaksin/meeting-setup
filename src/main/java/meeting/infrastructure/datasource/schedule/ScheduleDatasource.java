package meeting.infrastructure.datasource.schedule;

import meeting.application.service.schedule.ScheduleRepository;
import meeting.domain.model.meeting.MeetingNumber;
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
    public Schedules findByMeeingId(MeetingNumber meetingNumber) {
        List<Schedule> scheduleList = scheduleMapper.selectSchedulesByMeetingId(meetingNumber);
        if (scheduleList.isEmpty()) {
            throw new IllegalArgumentException(String.format("ミーティングの日程候補が見つかりませんでした。主催者に次のミーティング番号を確認してください。: %s", meetingNumber.value()));
        }

        return new Schedules(scheduleList);
    }
}
