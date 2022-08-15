package meeting.domain.model.meeting;

import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング作成依頼
 */
public class MeetingCreationRequest extends MeetingRegistrationRequest {

    @Deprecated
    MeetingCreationRequest() {}

    public MeetingCreationRequest(@Valid MeetingNumber meetingNumber,
                                  @Valid Subject subject,
                                  @Valid Memo memo,
                                  @Valid Schedules schedules,
                                  @Valid OrganizerNumber organizerNumber,
                                  @Valid RegistrationRequestDateTime registrationRequestDateTime) {
        super(meetingNumber, subject, memo, schedules, organizerNumber, registrationRequestDateTime);
    }
}
