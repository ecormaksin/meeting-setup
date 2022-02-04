package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 日程候補
 */
@ScheduleIsFutureWhenToAdd
public class Schedule {
    ScheduleNumber scheduleNumber;

    @Valid
    ScheduleConstruct scheduleConstruct;

    public Schedule(ScheduleNumber scheduleNumber, ScheduleConstruct scheduleConstruct) {
        this.scheduleNumber = scheduleNumber;
        this.scheduleConstruct = scheduleConstruct;
    }

    public static Schedule of(LocalDate noWrappedMeetingDate,
                              LocalTime noWrappedStartTime,
                              LocalTime noWrappedEndTime) {
        return new Schedule(
                ScheduleNumber.empty(),
                ScheduleConstruct.of(noWrappedMeetingDate, noWrappedStartTime, noWrappedEndTime)
        );
    }

    public static Schedule of(int primitiveScheduleNumber,
                               LocalDate noWrappedMeetingDate,
                               LocalTime noWrappedStartTime,
                               LocalTime noWrappedEndTime) {
        return new Schedule(
                new ScheduleNumber(primitiveScheduleNumber),
                ScheduleConstruct.of(noWrappedMeetingDate, noWrappedStartTime, noWrappedEndTime)
        );
    }

    public static Schedule empty() {
        return new Schedule(ScheduleNumber.empty(), ScheduleConstruct.empty());
    }

    @Deprecated
    Schedule() {}

    public ScheduleNumber scheduleNumber() {
        return scheduleNumber;
    }

    public ScheduleConstruct scheduleConstruct() {
        return scheduleConstruct;
    }

    public ScheduleValidity validity() {
        if (scheduleConstruct.validity() == ScheduleConstructValidity.妥当_すべて未入力) return ScheduleValidity.妥当_未入力;
        if (scheduleConstruct.validity() == ScheduleConstructValidity.不当_一部未入力あり) return ScheduleValidity.不当_構成要素の問題;
        if (scheduleConstruct.validity() == ScheduleConstructValidity.不当_時刻の前後関係が不正) return ScheduleValidity.不当_構成要素の問題;

        if (scheduleNumberIssuedStatus() == ScheduleNumberIssuedStatus.発番済) return ScheduleValidity.妥当;

        if (startDateTime().isBefore(LocalDateTime.now())) return ScheduleValidity.不当_追加時に過去;
        return ScheduleValidity.妥当;
    }

    public ScheduleNumberIssuedStatus scheduleNumberIssuedStatus() {
        if (Objects.isNull(scheduleNumber)) return ScheduleNumberIssuedStatus.未発番;
        return scheduleNumber.issuedStatus();
    }

    public MeetingDate meetingDate() {
        return scheduleConstruct.meetingDate();
    }

    public StartTime startTime() {
        return scheduleConstruct.startTime();
    }

    public EndTime endTime() {
        return scheduleConstruct.endTime();
    }

    public LocalDateTime startDateTime() {
        return LocalDateTime.of(meetingDate().value(), startTime().value());
    }

    public LocalDateTime endDateTime() {
        return LocalDateTime.of(meetingDate().value(), endTime().value());
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
