package meeting.infrastructure.datasource.participant;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.participant.Participant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParticipantMapper {
    List<Participant> selectByMeetingId(MeetingId meetingId);
}
