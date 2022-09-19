package meeting.presentation.participant;

import meeting.domain.model.participant.ParticipantNumber;

import java.util.Objects;

public class ParticipantNumberProxy {

    private final ParticipantNumber participantNumber;

    public ParticipantNumberProxy() {
        this.participantNumber = ParticipantNumber.newNumber();
    }

    public ParticipantNumber participantNumber() {
        return participantNumber;
    }

    public ParticipantNumber participantNumber(final String value) {
        return (Objects.isNull(value) || value.isBlank())
                ? participantNumber()
                : ParticipantNumber.of(value);
    }
}
