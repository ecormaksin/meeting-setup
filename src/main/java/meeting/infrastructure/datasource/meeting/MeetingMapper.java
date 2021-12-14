package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.meeting.organizer.IpAddress;
import meeting.domain.model.meeting.organizer.OSFamily;
import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.meeting.organizer.UserAgentFamily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MeetingMapper {
    void insertMeeting(MeetingId meetingId, Subject subject);
    void insertMeetingMemo(MeetingId meetingId, Memo memo);
    void insertOrganizerTerminal(MeetingId meetingId, IpAddress ipAddress, OSFamily osFamily, UserAgentFamily userAgentFamily);

    Optional<Subject> selectSubject(@Param("meetingId") MeetingId meetingId);
    Optional<Memo> selectMemo(@Param("meetingId") MeetingId meetingId);
    Optional<OrganizerTerminal> selectOrganizerTerminal(@Param("meetingId") MeetingId meetingId);
}
