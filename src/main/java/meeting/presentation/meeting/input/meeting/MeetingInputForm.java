package meeting.presentation.meeting.input.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.meeting.OrganizerNumber;
import meeting.domain.model.meeting.Subject;

import javax.validation.Valid;

public class MeetingInputForm {

    @Valid
    MeetingNumber meetingNumber;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    OrganizerNumber organizerNumber;

    public MeetingInputForm(@Valid MeetingNumber meetingNumber,
                            @Valid Subject subject,
                            @Valid Memo memo,
                            @Valid OrganizerNumber organizerNumber) {

    }
}
