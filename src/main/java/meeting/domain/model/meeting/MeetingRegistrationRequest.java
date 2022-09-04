package meeting.domain.model.meeting;

import meeting.domain.ValidationConcern;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

/**
 * ミーティング登録依頼
 */
public abstract class MeetingRegistrationRequest extends ValidationConcern {

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

    private boolean isSchedulesSetOfValidStartDateTime;

    @Deprecated
    MeetingRegistrationRequest() {}

    public MeetingRegistrationRequest(@Valid final MeetingNumber meetingNumber,
                                      @Valid final Subject subject,
                                      @Valid final Memo memo,
                                      @Valid final Schedules schedules,
                                      @Valid final OrganizerNumber organizerNumber,
                                      @Valid final RegistrationRequestDateTime registrationRequestDateTime) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.organizerNumber = organizerNumber;
        this.registrationRequestDateTime = registrationRequestDateTime;

        isSchedulesSetOfValidStartDateTime = schedules.isSetOfValidMeetingDateTimes(registrationRequestDateTime);

        this.validate(this);
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
