package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.meeting.meeting_list.MeetingListItem;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.meeting.organizer.IpAddress;
import meeting.domain.model.meeting.organizer.OSFamily;
import meeting.domain.model.meeting.organizer.UserAgentFamily;
import meeting.domain.model.meeting.search_condition.Keyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MeetingMapper {
    void insertMeeting(@Param("meetingNumber") MeetingNumber meetingNumber, @Param("subject") Subject subject);
    void insertMeetingMemo(@Param("meetingNumber") MeetingNumber meetingNumber, @Param("memo") Memo memo);

    Optional<Subject> selectSubject(@Param("meetingNumber") MeetingNumber meetingNumber);
    Optional<Memo> selectMemo(@Param("meetingNumber") MeetingNumber meetingNumber);
    Optional<ClientBrowser> selectOrganizerTerminal(@Param("meetingNumber") MeetingNumber meetingNumber);

    void deleteOrganizerTerminal(@Param("meetingNumber") MeetingNumber meetingNumber);
    void deleteMeetingMemo(@Param("meetingNumber") MeetingNumber meetingNumber);
    void deleteMeeting(@Param("meetingNumber") MeetingNumber meetingNumber);

    List<MeetingListItem> organizerMeetingListItems(
            @Param("ipAddress") IpAddress ipAddress,
            @Param("osFamily") OSFamily osFamily,
            @Param("userAgentFamily") UserAgentFamily userAgentFamily);

    List<MeetingListItem> search(
            @Param("ipAddress") IpAddress ipAddress,
            @Param("osFamily") OSFamily osFamily,
            @Param("userAgentFamily") UserAgentFamily userAgentFamily,
            @Param("keyword") Keyword keyword,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
