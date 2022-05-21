package meeting.domain.model.meeting;

import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング作成依頼
 */
public class MeetingCreationRequest {

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
    MeetingCreationRequest() {}

    public MeetingCreationRequest(@Valid MeetingNumber meetingNumber,
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
