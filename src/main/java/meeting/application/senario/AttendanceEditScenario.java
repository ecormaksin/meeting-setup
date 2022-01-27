package meeting.application.senario;

import meeting.application.service.attendance.AttendanceQueryService;
import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceEditRequest;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Service;

/**
 * 出欠編集シナリオ
 */
@Service
public class AttendanceEditScenario {
    AttendanceRecordService attendanceRecordService;
    AttendanceQueryService attendanceQueryService;
    ScheduleQueryService scheduleQueryService;

    public AttendanceEditScenario(AttendanceRecordService attendanceRecordService,
                                  AttendanceQueryService attendanceQueryService,
                                  ScheduleQueryService scheduleQueryService) {
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
        this.scheduleQueryService = scheduleQueryService;
    }

    public Attendance attendanceOf(AttendanceId attendanceId) {
        return attendanceQueryService.attendanceOf(attendanceId);
    }

    public void edit(MeetingId meetingId, AttendanceEditRequest attendanceEdittRequest) {
        attendanceRecordService.edit(meetingId, attendanceEdittRequest);
    }

    public Schedules schedules(MeetingId meetingId) {
        return scheduleQueryService.schedules(meetingId);
    }
}
