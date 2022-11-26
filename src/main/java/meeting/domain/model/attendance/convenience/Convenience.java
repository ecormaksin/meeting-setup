package meeting.domain.model.attendance.convenience;

import meeting.domain.model.schedule.ScheduleNumber;

import javax.validation.Valid;

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

    public static Convenience of(ScheduleNumber scheduleNumber,
                       ConvenienceCategory convenienceCategory) {
        return new Convenience(scheduleNumber, convenienceCategory);
    }

    public ScheduleNumber scheduleNumber() {
        return scheduleNumber;
    }

    public ConvenienceCategory convenienceCategory() {
        return convenienceCategory;
    }

    @Override
    public String toString() {
        return """
                {
                    "scheduleNumber": %s,
                    "convenienceCategory": "%s"
                }
                """.formatted(scheduleNumber.toString(), convenienceCategory.toString());
    }
}
