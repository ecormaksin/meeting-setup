package example.datasource.meeting;

import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.Memo;
import example.domain.model.meeting.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MeetingMapper {

    void insertMeeting(
            @Param("meetingId") MeetingId meetingId,
            @Param("subject") Subject subject);

    void insertMeetingMemo(
            @Param("meetingId") MeetingId meetingId,
            @Param("memo") Memo memo);
}
