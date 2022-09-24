package meeting.infrastructure.datasource.participant;

import meeting.application.service.participant.ParticipantRepository;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDatasource implements ParticipantRepository {

    ParticipantMapper participantMapper;

    ParticipantDatasource(ParticipantMapper participantMapper) {
        this.participantMapper = participantMapper;
    }

    @Override
    public ParticipantName getLatestParticipantName(ParticipantNumber participantNumber) {
        return participantMapper.selectLatestParticipantName(participantNumber);
    }
}
