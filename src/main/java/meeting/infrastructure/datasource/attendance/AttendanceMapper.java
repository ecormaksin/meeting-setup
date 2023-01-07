package meeting.infrastructure.datasource.attendance;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceComment;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.schedule.ScheduleNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    boolean attendanceInputDuplicated(@Param("meetingNumber") MeetingNumber meetingNumber,
                                      @Param("participantNumber") ParticipantNumber participantNumber);

    boolean participantNameDuplicated(@Param("meetingNumber") MeetingNumber meetingNumber,
                                      @Param("attendanceNumber") AttendanceNumber attendanceNumber,
                                      @Param("participantName") ParticipantName participantName);

    void insertAttendance(@Param("meetingNumber") MeetingNumber meetingNumber,
                          @Param("attendanceNumber") AttendanceNumber attendanceNumber,
                          @Param("participantNumber") ParticipantNumber participantNumber,
                          @Param("participantName") ParticipantName participantName);

    int newConvenienceNumber();
    void insertConvenience(@Param("convenienceNumber") int convenienceNumber,
                           @Param("attendanceNumber") AttendanceNumber attendanceNumber,
                           @Param("scheduleNumber") ScheduleNumber scheduleNumber,
                           @Param("convenienceCategory") ConvenienceCategory convenienceCategory);

    void insertAttendanceComment(@Param("attendanceNumber") AttendanceNumber attendanceNumber,
                                 @Param("attendanceComment") AttendanceComment attendanceComment);

    Attendance selectAttendance(@Param("attendanceNumber") AttendanceNumber attendanceNumber);
    List<Attendance> selectAttendancesByMeetingNumber(@Param("meetingNumber") MeetingNumber meetingNumber);
    List<Convenience> selectConveniencesByAttendanceNumber(@Param("attendanceNumber") AttendanceNumber attendanceNumber);

    void deleteAttendanceComment(@Param("attendanceNumber") AttendanceNumber attendanceNumber);
    void deleteConveniences(@Param("attendanceNumber") AttendanceNumber attendanceNumber);
    void deleteAttendance(@Param("attendanceNumber") AttendanceNumber attendanceNumber);
}
