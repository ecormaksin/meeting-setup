package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.meeting_list.MeetingListItem;
import meeting.domain.model.meeting.search_condition.Keyword;
import meeting.domain.model.organizer.OrganizerNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MeetingMapper {
    void insertMeeting(@Param("meetingNumber") MeetingNumber meetingNumber,
                       @Param("subject") Subject subject);
    void insertMeetingMemo(@Param("meetingNumber") MeetingNumber meetingNumber, @Param("memo") Memo memo);

    Optional<MeetingWithoutSchedulesAndAttendances> selectMeeting(@Param("meetingNumber") MeetingNumber meetingNumber);

    void deleteMeetingMemo(@Param("meetingNumber") MeetingNumber meetingNumber);
    void deleteMeeting(@Param("meetingNumber") MeetingNumber meetingNumber);

    List<MeetingListItem> organizerMeetingListItems(
            @Param("organizerNumber") OrganizerNumber organizerNumber);

    List<MeetingListItem> search(
            @Param("organizerNumber") OrganizerNumber organizerNumber,
            @Param("keyword") Keyword keyword,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
