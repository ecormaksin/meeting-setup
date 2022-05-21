package meeting.infrastructure.datasource.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceComment;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.ParticipantName;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.ScheduleNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    boolean participantNameDuplicated(@Param("meetingNumber") MeetingNumber meetingNumber,
                                      @Param("attendanceId") AttendanceId attendanceId,
                                      @Param("participantName") ParticipantName participantName);

    void insertAttendance(@Param("meetingNumber") MeetingNumber meetingNumber,
                          @Param("attendanceId") AttendanceId attendanceId,
                          @Param("participantName") ParticipantName participantName);

    int newConvenienceNumber();
    void insertConvenience(@Param("convenienceNumber") int convenienceNumber,
                           @Param("attendanceId") AttendanceId attendanceId,
                           @Param("scheduleNumber") ScheduleNumber scheduleNumber,
                           @Param("convenienceCategory") ConvenienceCategory convenienceCategory);

    void insertAttendanceComment(@Param("attendanceId") AttendanceId attendanceId,
                                 @Param("attendanceComment") AttendanceComment attendanceComment);

    Attendance selectAttendance(@Param("attendanceId") AttendanceId attendanceId);
    List<Attendance> selectAttendancesByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);
    List<Convenience> selectConveniencesByAttendanceId(@Param("attendanceId") AttendanceId attendanceId);

    void deleteAttendanceCommentsByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);
    void deleteConveniencesByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);
    void deleteAttendancesByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);

    void deleteAttendanceComment(@Param("attendanceId") AttendanceId attendanceId);
    void deleteConveniences(@Param("attendanceId") AttendanceId attendanceId);
    void deleteAttendance(@Param("attendanceId") AttendanceId attendanceId);
}
