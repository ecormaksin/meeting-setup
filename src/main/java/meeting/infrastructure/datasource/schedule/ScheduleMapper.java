package meeting.infrastructure.datasource.schedule;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.construct.Duration;
import meeting.domain.model.schedule.construct.MeetingDate;
import meeting.domain.model.schedule.construct.StartHour;
import meeting.domain.model.schedule.construct.StartMinute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    int newScheduleNumber();
    void insertSchedule(@Param("scheduleNumber") ScheduleNumber scheduleNumber,
                        @Param("meetingNumber") MeetingNumber meetingNumber,
                        @Param("meetingDate") MeetingDate meetingDate,
                        @Param("startHour") StartHour startHour,
                        @Param("startMinute") StartMinute startMinute,
                        @Param("duration") Duration duration);

    List<Schedule> selectSchedulesByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);

    void deleteSchedulesByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);
}
