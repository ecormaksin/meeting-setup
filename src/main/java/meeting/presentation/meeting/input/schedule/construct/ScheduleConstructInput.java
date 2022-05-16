package meeting.presentation.meeting.input.schedule.construct;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @NotNull
    LocalDateTime dateTimeToCompare;

    /**
     * @deprecated Use AllArgsConstructor or empty()
     */
    @Deprecated
    ScheduleConstructInput() {}

    public ScheduleConstructInput(@NotNull @Valid final MeetingDateInput meetingDate,
                                  @NotNull @Valid final StartHourInput startHour,
                                  @NotNull @Valid final StartMinuteInput startMinute,
                                  @NotNull @Valid final DurationInput duration,
                                  @NotNull final LocalDateTime dateTimeToCompare) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;
        this.dateTimeToCompare = dateTimeToCompare;
    }

    public static ScheduleConstructInput empty() {
        return empty(LocalDateTime.now());
    }

    public static ScheduleConstructInput empty(@NotNull final LocalDateTime dateTimeToCompare) {
        return new ScheduleConstructInput(MeetingDateInput.empty(),
                StartHourInput.empty(),
                StartMinuteInput.empty(),
                DurationInput.empty(),
                LocalDateTime.now());
    }

    @AssertTrue(message = "日程のすべての項目を入力してください。")
    boolean isValid() {

        List<Element> elements = Arrays.asList(
                meetingDate,
                startHour,
                startMinute,
                duration);

        final boolean allEmpty = elements.stream().allMatch(e -> e.isEmpty());
        if (allEmpty) return true;

        final boolean allValidAsDomain = elements.stream().allMatch(e -> e.isValidAsDomain());
        if (allValidAsDomain) return true;

        return false;
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
