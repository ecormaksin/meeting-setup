package meeting.infrastructure.datasource.participant;

import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ParticipantMapper {

    boolean notExists(@Param("participantNumber") ParticipantNumber participantNumber);

    void insertParticipant(@Param("participantNumber") ParticipantNumber participantNumber);

    ParticipantName selectLatestParticipantName(@Param("participantNumber") ParticipantNumber participantNumber);

    void insertLatestParticipantName(
            @Param("participantNumber") ParticipantNumber participantNumber,
            @Param("participantName") ParticipantName participantName);

    void deleteLatestParticipantName(
            @Param("participantNumber") ParticipantNumber participantNumber);
}
