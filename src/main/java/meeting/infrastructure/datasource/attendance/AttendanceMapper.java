package meeting.infrastructure.datasource.attendance;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.attendance.Attendance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    List<Attendance> selectByMeetingId(MeetingId meetingId);
}
