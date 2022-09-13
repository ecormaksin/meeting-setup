package meeting.application.senario;

import meeting.application.service.attendance.AttendanceQueryService;
import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.meeting.MeetingNumber;
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

    public AttendanceNumber input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        return attendanceRecordService.input(meetingNumber, attendanceInputRequest);
    }

    public Schedules schedules(MeetingNumber meetingNumber) {
        return scheduleQueryService.schedules(meetingNumber);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingNumber meetingNumber, ParticipantName participantName) {
        return attendanceQueryService.participantNameDuplicationStatus(meetingNumber, participantName);
    }
}
