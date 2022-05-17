package meeting.presentation.meeting.input.schedule.construct;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 日程の構成要素（入力用）
 */
public class ScheduleConstructInput {

    @NotNull
    @Valid
    MeetingDateInput meetingDate;

    @NotNull
    @Valid
    StartHourInput startHour;

    @NotNull
    @Valid
    StartMinuteInput startMinute;

    @NotNull
    @Valid
    DurationInput duration;

    private List<Element> elements;

    /**
     * @deprecated Use AllArgsConstructor or empty()
     */
    @Deprecated
    ScheduleConstructInput() {}

    public ScheduleConstructInput(@NotNull @Valid final MeetingDateInput meetingDate,
                                  @NotNull @Valid final StartHourInput startHour,
                                  @NotNull @Valid final StartMinuteInput startMinute,
                                  @NotNull @Valid final DurationInput duration) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;

        elements = Arrays.asList(
                meetingDate,
                startHour,
                startMinute,
                duration);
    }

    public static ScheduleConstructInput empty() {
        return new ScheduleConstructInput(MeetingDateInput.empty(),
                StartHourInput.empty(),
                StartMinuteInput.empty(),
                DurationInput.empty());
    }

    @AssertTrue(message = "日程のすべての項目を入力してください。")
    boolean isValid() {
        if (areAllEmpty()) return true;
        if (areAllValidAsDomain()) return true;
        return false;
    }

    private boolean areAllEmpty() {
        return elements.stream().allMatch(e -> e.isEmpty());
    }

    private boolean areAllValidAsDomain() {
        return elements.stream().allMatch(e -> e.isValidAsDomain());
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
