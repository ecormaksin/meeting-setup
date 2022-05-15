package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.ScheduleNumber;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 日程（入力用）
 */
public class ScheduleInput {

    @NotNull
    @Valid
    ScheduleNumber scheduleNumber;

    @NotNull
    @Valid
    ScheduleConstructInput scheduleConstruct;

    @Deprecated
    ScheduleInput(){}

    public ScheduleInput(@NotNull @Valid final ScheduleNumber scheduleNumber,
                         @NotNull @Valid final ScheduleConstructInput scheduleConstruct) {
        this.scheduleNumber = scheduleNumber;
        this.scheduleConstruct = scheduleConstruct;
    }

    public static ScheduleInput newInput() {
        return new ScheduleInput(ScheduleNumber.prepare(), ScheduleConstructInput.empty());
    }
}
