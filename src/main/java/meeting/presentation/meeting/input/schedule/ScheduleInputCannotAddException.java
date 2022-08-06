package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.Schedules;

public class ScheduleInputCannotAddException extends RuntimeException {

    public ScheduleInputCannotAddException() {
        super(String.format("%d件を超える日程は追加できません。", Schedules.MAX_SIZE));
    }
}
