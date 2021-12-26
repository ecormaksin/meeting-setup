package meeting.application.senario;

import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Service;

/**
 * 出欠入力シナリオ
 */
@Service
public class AttendanceInputScenario {
    AttendanceRecordService attendanceRecordService;
    ScheduleQueryService scheduleQueryService;

    public AttendanceInputScenario(AttendanceRecordService attendanceRecordService,
                                   ScheduleQueryService scheduleQueryService) {
        this.attendanceRecordService = attendanceRecordService;
        this.scheduleQueryService = scheduleQueryService;
    }

    public AttendanceId input(AttendanceInputRequest attendanceInputRequest) {
        return attendanceRecordService.input(attendanceInputRequest);
    }

    public Schedules schedules(MeetingId meetingId) {
        return scheduleQueryService.schedules(meetingId);
    }
}
