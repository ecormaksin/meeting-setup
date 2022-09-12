package meeting.presentation.organizer;

import meeting.domain.model.organizer.OrganizerNumber;

public class OrganizerNumberProxy {

    private final OrganizerNumber organizerNumber;

    public OrganizerNumberProxy() {
        this.organizerNumber = OrganizerNumber.newNumber();
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }
}
