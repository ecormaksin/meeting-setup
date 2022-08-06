package meeting.domain.model.attendance.convenience;

import meeting.domain.model.schedule.ScheduleNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 都合
 */
public class Convenience {

    @Valid
    ScheduleNumber scheduleNumber;

    @Valid
    ConvenienceCategory convenienceCategory;

    @Deprecated
    Convenience() {}

    public Convenience(ScheduleNumber scheduleNumber,
                       ConvenienceCategory convenienceCategory) {
        this.scheduleNumber = scheduleNumber;
        this.convenienceCategory = convenienceCategory;
    }

    public ScheduleNumber scheduleNumber() {
        return scheduleNumber;
    }

    public ConvenienceCategory convenienceCategory() {
        return convenienceCategory;
    }
}
