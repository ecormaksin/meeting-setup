package meeting.domain.model.meeting;

import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング登録依頼
 */
public class MeetingRegistrationRequest {

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

    @Deprecated
    MeetingRegistrationRequest() {}

    public MeetingRegistrationRequest(@Valid MeetingNumber meetingNumber,
                                      @Valid Subject subject,
                                      @Valid Memo memo,
                                      @Valid OrganizerNumber organizerNumber,
                                      @Valid Schedules schedules) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.organizerNumber = organizerNumber;
        this.schedules = schedules;
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
