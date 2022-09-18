package meeting.presentation.participant;

import meeting.domain.model.participant.ParticipantNumber;

public class ParticipantNumberProxy {

    private final ParticipantNumber participantNumber;

    public ParticipantNumberProxy() {
        this.participantNumber = ParticipantNumber.newNumber();
    }

    public ParticipantNumber participantNumber() {
        return participantNumber;
    }
}
