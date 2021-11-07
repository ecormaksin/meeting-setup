package example.domain.model.schedule;

import example.domain.model.schedule.construct.ScheduleConstruct;

import javax.validation.Valid;

/**
 * 候補日程
 */
public class Schedule {
    ScheduleNumber scheduleNumber;

    @Valid
    ScheduleConstruct scheduleConstruct;

    public ScheduleConstruct scheduleConstruct() {
        return scheduleConstruct;
    }

    public boolean isConstructNotNull() {
        return scheduleConstruct.isNotNull();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Schedule{" +
                "scheduleNumber=" + scheduleNumber +
                ", scheduleConstruct=" + scheduleConstruct +
                '}';
        // @formatter:on
    }
}
