package meeting.presentation.organizer;

import meeting.domain.model.organizer.OrganizerNumber;

import java.util.Objects;

public class OrganizerNumberProxy {

    private final OrganizerNumber organizerNumber;

    public OrganizerNumberProxy() {
        this.organizerNumber = OrganizerNumber.newNumber();
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }

    public OrganizerNumber organizerNumber(final String value) {
        return (Objects.isNull(value) || value.isBlank())
                ? organizerNumber()
                : OrganizerNumber.of(value);
    }
}
