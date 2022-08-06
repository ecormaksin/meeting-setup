package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;

import javax.validation.Valid;

/**
 * 日程（入力用）
 */
public class ScheduleInput {

    @Valid
    ScheduleNumber scheduleNumber;

    @Valid
    ScheduleConstructInput scheduleConstruct;

    @Deprecated
    ScheduleInput(){}

    public ScheduleInput(final ScheduleNumber scheduleNumber,
                         final ScheduleConstructInput scheduleConstruct) {
        this.scheduleNumber = scheduleNumber;
        this.scheduleConstruct = scheduleConstruct;
    }

    public static ScheduleInput newInput() {
        return new ScheduleInput(ScheduleNumber.initialized(), ScheduleConstructInput.empty());
    }

    public static ScheduleInput fromSchedule(final Schedule schedule) {
        final ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
        return new ScheduleInput(
                schedule.scheduleNumber(),
                ScheduleConstructInput.fromScheduleConstruct(scheduleConstruct));
    }

    public boolean isEmpty() {
        return scheduleConstruct.areAllEmpty();
    }

    public boolean isValidAsDomain() {
        return scheduleConstruct.areAllValidAsDomain();
    }

    public Schedule toSchedule() {
        return new Schedule(scheduleNumber, scheduleConstruct.toScheduleConstruct());
    }

    public ScheduleConstructInput scheduleConstructInput() {
        return scheduleConstruct;
    }
}
