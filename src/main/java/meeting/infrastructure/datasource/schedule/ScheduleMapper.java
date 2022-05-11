package meeting.infrastructure.datasource.schedule;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    int newScheduleNumber();
    void insertSchedule(@Param("scheduleNumber") int scheduleNumber,
                        @Param("meetingNumber") MeetingNumber meetingNumber,
                        @Param("startDateTime") LocalDateTime startDateTime,
                        @Param("endDateTime") LocalDateTime endDateTime);

    List<Schedule> selectSchedulesByMeetingId(@Param("meetingNumber") MeetingNumber meetingNumber);

    void deleteSchedulesByMeetingId(@Param("meetingNumber") MeetingNumber meetingNumber);
}
