package meeting.domain.model.meeting;

import meeting.domain.ValidationConcern;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.ScheduleRegistrationStatus;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.SchedulesInvalidException;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;

/**
 * ミーティング登録依頼
 */
public class MeetingRegistrationRequest extends ValidationConcern {

    @Valid
    MeetingNumber meetingNumber;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    OrganizerNumber organizerNumber;

    @Valid
    Schedules schedules;

    @Valid
    RegistrationRequestDateTime registrationRequestDateTime;

    boolean isSchedulesSetOfValidStartDateTime;

    @Deprecated
    MeetingRegistrationRequest() {}

    public MeetingRegistrationRequest(@Valid MeetingNumber meetingNumber,
                                      @Valid Subject subject,
                                      @Valid Memo memo,
                                      @Valid Schedules schedules,
                                      @Valid OrganizerNumber organizerNumber,
                                      @Valid RegistrationRequestDateTime registrationRequestDateTime) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.organizerNumber = organizerNumber;
        this.registrationRequestDateTime = registrationRequestDateTime;

        isSchedulesSetOfValidStartDateTime = isSetOfValidMeetingDateTimes();

        try {
            this.validate(this);
        } catch (IllegalArgumentException e) {
            throw new MeetingRegistrationRequestInvalidException(e.getMessage());
        }
    }

    private boolean isSetOfValidMeetingDateTimes() {
        return schedules.asList().stream().allMatch(schedule -> {
            if (schedule.registrationStatus() == ScheduleRegistrationStatus.編集) return true;

            LocalDateTime startLocalDateTime = schedule.startLocalDateTime();
            return startLocalDateTime.isAfter(registrationRequestDateTime.value());
        });
    }

    @AssertTrue(message = "日程を追加する時は未来の日時を指定してください。")
    boolean isSchedulesSetOfValidStartDateTime() {
        return isSchedulesSetOfValidStartDateTime;
    }

    public MeetingNumber meetingNumber() {
        return meetingNumber;
    }

    public Subject subject() {
        return subject;
    }

    public Memo memo() {
        return memo;
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }

    public Schedules schedules() {
        return schedules;
    }
}
