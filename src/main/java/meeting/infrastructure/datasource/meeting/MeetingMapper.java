package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.meeting.OrganizerMeetingListItem;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.meeting.organizer.IpAddress;
import meeting.domain.model.meeting.organizer.OSFamily;
import meeting.domain.model.meeting.organizer.UserAgentFamily;
import meeting.domain.model.meeting.search_condition.EndDate;
import meeting.domain.model.meeting.search_condition.Keyword;
import meeting.domain.model.meeting.search_condition.StartDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MeetingMapper {
    void insertMeeting(@Param("meetingId") MeetingId meetingId, @Param("subject") Subject subject);
    void insertMeetingMemo(@Param("meetingId") MeetingId meetingId, @Param("memo") Memo memo);
    void insertOrganizerTerminal(
            @Param("meetingId") MeetingId meetingId,
            @Param("ipAddress") IpAddress ipAddress,
            @Param("osFamily") OSFamily osFamily,
            @Param("userAgentFamily") UserAgentFamily userAgentFamily);

    Optional<Subject> selectSubject(@Param("meetingId") MeetingId meetingId);
    Optional<Memo> selectMemo(@Param("meetingId") MeetingId meetingId);
    Optional<ClientBrowser> selectOrganizerTerminal(@Param("meetingId") MeetingId meetingId);

    void deleteOrganizerTerminal(@Param("meetingId") MeetingId meetingId);
    void deleteMeetingMemo(@Param("meetingId") MeetingId meetingId);
    void deleteMeeting(@Param("meetingId") MeetingId meetingId);

    List<OrganizerMeetingListItem> organizerMeetingListItems(
            @Param("ipAddress") IpAddress ipAddress,
            @Param("osFamily") OSFamily osFamily,
            @Param("userAgentFamily") UserAgentFamily userAgentFamily);

    List<OrganizerMeetingListItem> search(
            @Param("ipAddress") IpAddress ipAddress,
            @Param("osFamily") OSFamily osFamily,
            @Param("userAgentFamily") UserAgentFamily userAgentFamily,
            @Param("keyword") Keyword keyword,
            @Param("startDate") StartDate startDate,
            @Param("endDate") EndDate endDate);
}
