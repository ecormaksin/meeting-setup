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
                                  @Valid OrganizerNumber organizerNumber,
                                  @Valid Schedules schedules,
                                  @Valid RegistrationRequestDateTime registrationRequestDateTime) {
        super(meetingNumber, subject, memo, organizerNumber, schedules, registrationRequestDateTime);
    }
}
