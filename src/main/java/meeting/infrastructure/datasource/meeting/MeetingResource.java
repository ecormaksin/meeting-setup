package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.OrganizerNumber;
import meeting.domain.model.meeting.Subject;

public class MeetingResource {

    Subject subject;

    OrganizerNumber organizerNumber;

    @Deprecated
    MeetingResource() {}

    public MeetingResource(final Subject subject, final OrganizerNumber organizerNumber) {
        this.subject = subject;
        this.organizerNumber = organizerNumber;
    }

    public Subject subject() {
        return subject;
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }
}
