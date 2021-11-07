package example.datasource.schedule;

import example.domain.model.meeting.MeetingId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface ScheduleMapper {

    int newScheduleNumber();

    void insertSchedule(
            @Param("scheduleNumber") int scheduleNumber,
            @Param("meetingId") MeetingId meetingId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
