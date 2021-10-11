package example.domain.model.meeting;

import example.domain.model.meeting.attendance.Attendances;
import example.domain.model.meeting.schedule.Schedule;

/**
 * 候補日程
 */
public class ProposedSchedule {
    Schedule 日程;
    Attendances 出欠;

    @Override
    public String toString() {
        // @formatter:off
        return "ProposedSchedule{" +
                "日程=" + 日程 +
                ", 出欠=" + 出欠 +
                '}';
        // @formatter:on
    }
}
