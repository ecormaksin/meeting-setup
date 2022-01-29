package meeting.application.senario;

import meeting.application.service.attendance.AttendanceQueryService;
import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.attendance.ParticipantName;
import meeting.domain.model.attendance.ParticipantNameDuplicationStatus;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Service;

/**
 * 出欠入力シナリオ
 */
@Service
public class AttendanceInputScenario {
    AttendanceRecordService attendanceRecordService;
    AttendanceQueryService attendanceQueryService;
    ScheduleQueryService scheduleQueryService;

    public AttendanceInputScenario(AttendanceRecordService attendanceRecordService,
                                   AttendanceQueryService attendanceQueryService,
                                   ScheduleQueryService scheduleQueryService) {
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
        this.scheduleQueryService = scheduleQueryService;
    }

    public AttendanceId input(MeetingId meetingId, AttendanceInputRequest attendanceInputRequest) {
        return attendanceRecordService.input(meetingId, attendanceInputRequest);
    }

    public Schedules schedules(MeetingId meetingId) {
        return scheduleQueryService.schedules(meetingId);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingId meetingId, ParticipantName participantName) {
        return attendanceQueryService.participantNameDuplicationStatus(meetingId, participantName);
    }
}
