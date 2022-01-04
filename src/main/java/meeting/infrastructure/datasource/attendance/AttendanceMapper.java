package meeting.infrastructure.datasource.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceComment;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.ParticipantName;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.ScheduleNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    void insertAttendance(MeetingId meetingId, AttendanceId attendanceId, ParticipantName participantName);

    int newConvenienceNumber();
    void insertConvenience(int convenienceNumber,
                           AttendanceId attendanceId,
                           ScheduleNumber scheduleNumber,
                           ConvenienceCategory convenienceCategory);

    void insertAttendanceComment(AttendanceId attendanceId, AttendanceComment attendanceComment);

    List<Attendance> selectAttendancesByMeetingId(@Param("meetingId") MeetingId meetingId);
    List<Convenience> selectConveniencesByAttendanceId(@Param("attendanceId") AttendanceId attendanceId);
}
