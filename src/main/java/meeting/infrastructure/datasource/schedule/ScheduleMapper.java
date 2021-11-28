package meeting.infrastructure.datasource.schedule;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    int newScheduleNumber();
    void insertSchedule(int scheduleNumber, MeetingId meetingId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Schedule> selectByMeetingId(MeetingId meetingId);
}
