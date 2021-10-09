package example.domain.model.meeting;

import example.domain.model.meeting.schedule.Schedule;

/**
 * 候補日程
 */
public class ProposedSchedule {
    ProposedScheduleNumber 候補日程番号;
    Schedule 日程;

    @Override
    public String toString() {
        // @formatter:off
        return "ProposedSchedule{" +
                "候補日程番号=" + 候補日程番号 +
                ", 日程=" + 日程 +
                '}';
        // @formatter:on
    }
}
