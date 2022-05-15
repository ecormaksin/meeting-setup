package meeting.infrastructure.datasource.schedule;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.construct.MeetingDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    int newScheduleNumber();
    void insertSchedule(@Param("scheduleNumber") ScheduleNumber scheduleNumber,
                        @Param("meetingNumber") MeetingNumber meetingNumber,
                        @Param("meetingDate") MeetingDate meetingDate,
                        @Param("startHourValue") String startHourValue,
                        @Param("startMinuteLabel") String startMinuteLabel,
                        @Param("durationValue") String durationValue);

    List<Schedule> selectSchedulesByMeetingId(@Param("meetingNumber") MeetingNumber meetingNumber);

    void deleteSchedulesByMeetingId(@Param("meetingNumber") MeetingNumber meetingNumber);
}
