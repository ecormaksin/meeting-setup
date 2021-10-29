package example.datasource.schedule;

import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.Memo;
import example.domain.model.meeting.Subject;
import example.domain.model.schedule.EndTime;
import example.domain.model.schedule.StartTime;
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
