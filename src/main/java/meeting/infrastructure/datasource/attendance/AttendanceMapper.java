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
    void insertAttendance(@Param("meetingId") MeetingId meetingId,
                          @Param("attendanceId") AttendanceId attendanceId,
                          @Param("participantName") ParticipantName participantName);

    int newConvenienceNumber();
    void insertConvenience(@Param("convenienceNumber") int convenienceNumber,
                           @Param("attendanceId") AttendanceId attendanceId,
                           @Param("scheduleNumber") ScheduleNumber scheduleNumber,
                           @Param("convenienceCategory") ConvenienceCategory convenienceCategory);

    void insertAttendanceComment(@Param("attendanceId") AttendanceId attendanceId,
                                 @Param("attendanceComment") AttendanceComment attendanceComment);

    List<Attendance> selectAttendancesByMeetingId(@Param("meetingId") MeetingId meetingId);
    List<Convenience> selectConveniencesByAttendanceId(@Param("attendanceId") AttendanceId attendanceId);

    void deleteAttendanceCommentsByMeetingId(@Param("meetingId") MeetingId meetingId);
    void deleteConveniencesByMeetingId(@Param("meetingId") MeetingId meetingId);
    void deleteAttendancesByMeetingId(@Param("meetingId") MeetingId meetingId);
}
