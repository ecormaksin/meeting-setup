package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.ScheduleNumber;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
        return new ScheduleInput(ScheduleNumber.initialize(), ScheduleConstructInput.empty());
    }

    public boolean isEmpty() {
        return scheduleConstruct.areAllEmpty();
    }

    public boolean isValidAsDomain() {
        return scheduleConstruct.areAllValidAsDomain();
    }

    public boolean isDuplicated(@NotNull @Valid ScheduleInput other) {
        return this.scheduleConstruct.isSame(other.scheduleConstruct);
    }

    public boolean isFutureWhenAdd(@NotNull final LocalDateTime dateTimeToCompare) {
        if (scheduleNumber.issued()) return true;
        return scheduleConstruct.isFuture(dateTimeToCompare);
    }
}
