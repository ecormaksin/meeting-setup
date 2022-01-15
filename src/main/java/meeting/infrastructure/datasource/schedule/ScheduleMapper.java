package meeting.infrastructure.datasource.schedule;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    int newScheduleNumber();
    void insertSchedule(@Param("scheduleNumber") int scheduleNumber,
                        @Param("meetingId") MeetingId meetingId,
                        @Param("startDateTime") LocalDateTime startDateTime,
                        @Param("endDateTime") LocalDateTime endDateTime);

    List<Schedule> selectSchedulesByMeetingId(@Param("meetingId") MeetingId meetingId);

    void deleteSchedulesByMeetingId(@Param("meetingId") MeetingId meetingId);
}
