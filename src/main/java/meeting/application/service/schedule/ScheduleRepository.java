package meeting.application.service.schedule;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedules;

/**
 * 日程候補 リポジトリ
 */
public interface ScheduleRepository {
    Schedules findByMeeingNumber(MeetingNumber meetingNumber);
}
