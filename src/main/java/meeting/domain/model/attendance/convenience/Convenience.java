package meeting.domain.model.attendance.convenience;

import meeting.domain.model.schedule.ScheduleNumber;

/**
 * 都合
 */
public class Convenience {
    ScheduleNumber scheduleNumber;

    ConvenienceCategory convenienceCategory;

    public static Convenience of(int primitiveScheduleNumber,
                                 String symbol) {
        return new Convenience(new ScheduleNumber(primitiveScheduleNumber),
                                ConvenienceCategory.fromSymbol(symbol));
    }

    public Convenience(ScheduleNumber scheduleNumber,
                       ConvenienceCategory convenienceCategory) {
        this.scheduleNumber = scheduleNumber;
        this.convenienceCategory = convenienceCategory;
    }

    @Deprecated
    Convenience() {}

    public ScheduleNumber scheduleNumber() {
        return scheduleNumber;
    }

    public ConvenienceCategory convenienceCategory() {
        return convenienceCategory;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Convenience{" +
                "scheduleNumber=" + scheduleNumber +
                ", convenienceCategory=" + convenienceCategory +
                '}';
        // @formatter:on
    }
}
