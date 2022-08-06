package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.*;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.util.Arrays;
import java.util.List;

/**
 * 日程の構成要素（入力用）
 */
public class ScheduleConstructInput {

    @Valid
    MeetingDateInput meetingDate;

    @Valid
    StartHourInput startHour;

    @Valid
    StartMinuteInput startMinute;

    @Valid
    DurationInput duration;

    @Deprecated
    ScheduleConstructInput() {}

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
        return new ScheduleConstructInput(
                MeetingDateInput.empty(),
                StartHourInput.empty(),
                StartMinuteInput.empty(),
                DurationInput.empty());
    }

    public static ScheduleConstructInput fromScheduleConstruct(final ScheduleConstruct scheduleConstruct) {
        final MeetingDate meetingDate = scheduleConstruct.meetingDate();
        final StartHour startHour = scheduleConstruct.startHour();
        final StartMinute startMinute = scheduleConstruct.startMinute();
        final Duration duration = scheduleConstruct.duration();

        return new ScheduleConstructInput(
                MeetingDateInput.of(meetingDate),
                StartHourInput.of(startHour),
                StartMinuteInput.of(startMinute),
                DurationInput.of(duration));
    }

    @AssertTrue(message = "日程のすべての項目を入力してください。")
    boolean isValid() {
        if (areAllEmpty()) return true;
        if (areAllValidAsDomain()) return true;
        return false;
    }

    private List<ScheduleConstructElement> elements() {
        return Arrays.asList(
                meetingDate,
                startHour,
                startMinute,
                duration);
    }

    public boolean areAllEmpty() {
        return elements().stream().allMatch(e -> e.isEmpty());
    }

    public boolean areAllValidAsDomain() {
        return elements().stream().allMatch(e -> e.isValidAsDomain());
    }

    private void validateAsDomain() {
        if (areAllValidAsDomain()) return;
        throw new IllegalStateException("ミーティングの開催日時として正しくありません。設定内容を確認してください。");
    }

    public boolean isSame(@Valid ScheduleConstructInput other) {

        return this.meetingDate.isSame(other.meetingDate)
                && this.startHour.isSame(other.startHour)
                && this.startMinute.isSame(other.startMinute)
                && this.duration.isSame(other.duration);
    }

    public ScheduleConstruct toScheduleConstruct() {
        validateAsDomain();
        return new ScheduleConstruct(meetingDate.toMeetingDate(),
                startHour.toStartHour(),
                startMinute.toStartMinute(),
                duration.toDuration());
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
