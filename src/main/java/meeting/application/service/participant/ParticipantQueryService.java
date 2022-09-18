package meeting.application.service.participant;

import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import org.springframework.stereotype.Service;

@Service
public class ParticipantQueryService {

    private final ParticipantRepository participantRepository;

    ParticipantQueryService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ParticipantName latestParticipantName(ParticipantNumber participantNumber) {
        return participantRepository.getLatestParticipantName(participantNumber);
    }
}
