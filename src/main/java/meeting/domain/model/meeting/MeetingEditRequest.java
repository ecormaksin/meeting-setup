package meeting.domain.model.meeting;

import meeting.domain.model.organizer.OrganizerNumber;
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
                              @Valid Schedules schedules,
                              @Valid OrganizerNumber organizerNumber,
                              @Valid RegistrationRequestDateTime registrationRequestDateTime) {
        super(meetingNumber, subject, memo, schedules, organizerNumber, registrationRequestDateTime);
    }
}
