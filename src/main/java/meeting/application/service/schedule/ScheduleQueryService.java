package meeting.application.service.schedule;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Service;

/**
 * 日程候補の参照
 */
@Service
public class ScheduleQueryService {
    ScheduleRepository scheduleRepository;

    ScheduleQueryService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedules schedules(MeetingNumber meetingNumber) {
        return scheduleRepository.findByMeeingId(meetingNumber);
    }
}
