package meeting.application.service.participant;

import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;

public interface ParticipantRepository {

    ParticipantName getLatestParticipantName(ParticipantNumber participantNumber);
}
