package meeting.application.senario;

import meeting.application.service.attendance.AttendanceQueryService;
import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.participant.ParticipantQueryService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberDuplicationStatus;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Service;

/**
 * 出欠入力シナリオ
 */
@Service
public class AttendanceInputScenario {

    private final ParticipantQueryService participantQueryService;
    private final AttendanceRecordService attendanceRecordService;
    private final AttendanceQueryService attendanceQueryService;
    private final ScheduleQueryService scheduleQueryService;

    public AttendanceInputScenario(
            ParticipantQueryService participantQueryService,
            AttendanceRecordService attendanceRecordService,
            AttendanceQueryService attendanceQueryService,
            ScheduleQueryService scheduleQueryService) {

        this.participantQueryService = participantQueryService;
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
        this.scheduleQueryService = scheduleQueryService;
    }

    public ParticipantName latestParticipantName(ParticipantNumber participantNumber) {
        return participantQueryService.latestParticipantName(participantNumber);
    }

    public Schedules schedules(MeetingNumber meetingNumber) {
        return scheduleQueryService.schedules(meetingNumber);
    }

    public void input(MeetingNumber meetingNumber, AttendanceInputRequest attendanceInputRequest) {
        attendanceRecordService.input(meetingNumber, attendanceInputRequest);
    }

    public ParticipantNumberDuplicationStatus participantNumberDuplicationStatus(MeetingNumber meetingNumber, ParticipantNumber participantNumber) {
        return attendanceQueryService.participantNumberDuplicationStatus(meetingNumber, participantNumber);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(MeetingNumber meetingNumber, ParticipantName participantName) {
        return attendanceQueryService.participantNameDuplicationStatus(meetingNumber, participantName);
    }
}
