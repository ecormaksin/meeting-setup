package meeting.domain.model.meeting;

import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング編集依頼
 */
public class MeetingEditRequest extends MeetingRegistrationRequest {

    @Deprecated
    MeetingEditRequest() {}

    public MeetingEditRequest(@Valid MeetingNumber meetingNumber,
                              @Valid Subject subject,
                              @Valid Memo memo,
                              @Valid OrganizerNumber organizerNumber,
                              @Valid Schedules schedules,
                              @Valid RegistrationRequestDateTime registrationRequestDateTime) {
        super(meetingNumber, subject, memo, organizerNumber, schedules, registrationRequestDateTime);
    }
}
