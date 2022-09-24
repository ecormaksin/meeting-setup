package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.*;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;

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

    boolean meetingDateNotEmpty;
    boolean startHourNotEmpty;
    boolean startMinuteNotEmpty;
    boolean durationNotEmpty;

    @Deprecated
    ScheduleConstructInput() {}

    public ScheduleConstructInput(MeetingDateInput meetingDate,
                                  StartHourInput startHour,
                                  StartMinuteInput startMinute,
                                  DurationInput duration) {
        this.meetingDate = meetingDate;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.duration = duration;

        meetingDateNotEmpty = isMeetingDateNotEmpty();
        startHourNotEmpty = isStartHourNotEmpty();
        startMinuteNotEmpty = isStartMinuteNotEmpty();
        durationNotEmpty = isDurationNotEmpty();
    }

    public static ScheduleConstructInput empty() {
        return new ScheduleConstructInput(
                MeetingDateInput.empty(),
                StartHourInput.empty(),
                StartMinuteInput.empty(),
                DurationInput.empty());
    }

    public static ScheduleConstructInput of(int year, int month, int dayOfMonth, int hour, int minute, int duration) {

        LocalDate meetingDateValue = LocalDate.of(year,month, dayOfMonth);

        return new ScheduleConstructInput(
                MeetingDateInput.of(meetingDateValue),
                StartHourInput.of(hour),
                StartMinuteInput.of(minute),
                DurationInput.of(duration));
    }

    public static ScheduleConstructInput fromScheduleConstruct(ScheduleConstruct scheduleConstruct) {
        MeetingDate meetingDate = scheduleConstruct.meetingDate();
        StartHour startHour = scheduleConstruct.startHour();
        StartMinute startMinute = scheduleConstruct.startMinute();
        Duration duration = scheduleConstruct.duration();

        return new ScheduleConstructInput(
                MeetingDateInput.of(meetingDate),
                StartHourInput.of(startHour),
                StartMinuteInput.of(startMinute),
                DurationInput.of(duration));
    }

    @AssertTrue(message = "開催日を入力してください。")
    boolean isMeetingDateNotEmpty() {
        if (areAllItemsEmpty()) return true;
        return !meetingDate.isEmpty();
    }

    @AssertTrue(message = "開始時間を入力してください。")
    boolean isStartHourNotEmpty() {
        if (areAllItemsEmpty()) return true;
        return !startHour.isEmpty();
    }

    @AssertTrue(message = "開始分を入力してください。")
    boolean isStartMinuteNotEmpty() {
        if (areAllItemsEmpty()) return true;
        return !startMinute.isEmpty();
    }

    @AssertTrue(message = "会議時間を入力してください。")
    boolean isDurationNotEmpty() {
        if (areAllItemsEmpty()) return true;
        return !duration.isEmpty();
    }

    public boolean areAllItemsEmpty() {
        return meetingDate.isEmpty()
                && startHour.isEmpty()
                && startMinute.isEmpty()
                && duration.isEmpty();
    }

    public boolean areAllItemsValidAsDomain() {
        return meetingDate.isValidAsDomain()
                && startHour.isValidAsDomain()
                && startMinute.isValidAsDomain()
                && duration.isValidAsDomain();
    }

    private void validateAsDomain() {
        if (areAllItemsValidAsDomain()) return;
        throw new IllegalStateException("ミーティングの開催日時として正しくありません。設定内容を確認してください。");
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
