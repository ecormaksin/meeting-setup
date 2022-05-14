package meeting.presentation.meeting.input.schedule.construct;

import javax.validation.constraints.AssertTrue;

/**
 * 日程の構成要素（入力用）
 */
public class ScheduleConstructInput {

    MeetingDateInput meetingDate;

    StartHourInput startHour;

    StartMinuteInput startMinute;

    DurationInput duration;

    @Deprecated
    ScheduleConstructInput() {}

    @AssertTrue
    public boolean isValid() {
        if (isAllEmpty()) return true;
        return true;
    }

    private boolean isAllEmpty() {

        return meetingDate.isEmpty()
                && startHour.isEmpty()
                && startMinute.isEmpty()
                && duration.isEmpty();
    }

    public ScheduleConstructInput(final MeetingDateInput meetingDate,
                                  final StartHourInput startHour,
                                  final StartMinuteInput startMinute,
                                  final DurationInput duration) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;
    }

    public static ScheduleConstructInput empty() {
        return new ScheduleConstructInput(MeetingDateInput.empty(),
                                            StartHourInput.empty(),
                                            StartMinuteInput.empty(),
                                            DurationInput.empty());
    }

    public MeetingDateInput meetingDate() {
        return meetingDate;
    }

    public StartHourInput startHour() {
        return startHour;
    }

    public StartMinuteInput startMinute() {
        return startMinute;
    }

    public DurationInput duration() {
        return duration;
    }
}
