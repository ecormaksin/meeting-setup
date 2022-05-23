package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
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

    public static ScheduleInput fromDomainObject(final Schedule schedule) {
        final ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
        return new ScheduleInput(
                schedule.scheduleNumber(),
                ScheduleConstructInput.fromDomainObject(scheduleConstruct));
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

    public Schedule toDomainObject() {
        return new Schedule(scheduleNumber, scheduleConstruct.toDomainObject());
    }

    public ScheduleConstructInput scheduleConstructInput() {
        return scheduleConstruct;
    }
}
