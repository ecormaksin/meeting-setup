package meeting.application.senario;

import meeting.application.service.attendance.AttendanceQueryService;
import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.participant.ParticipantQueryService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.*;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberDuplicationStatus;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Service;

/**
 * 出欠編集シナリオ
 */
@Service
public class AttendanceEditScenario {

    ParticipantQueryService participantQueryService;
    AttendanceRecordService attendanceRecordService;
    AttendanceQueryService attendanceQueryService;
    ScheduleQueryService scheduleQueryService;

    public AttendanceEditScenario(
            ParticipantQueryService participantQueryService,
            AttendanceRecordService attendanceRecordService,
            AttendanceQueryService attendanceQueryService,
            ScheduleQueryService scheduleQueryService) {

        this.participantQueryService = participantQueryService;
        this.attendanceRecordService = attendanceRecordService;
        this.attendanceQueryService = attendanceQueryService;
        this.scheduleQueryService = scheduleQueryService;
    }

    public Attendance attendanceOf(AttendanceNumber attendanceNumber) {
        return attendanceQueryService.attendanceOf(attendanceNumber);
    }

    public void edit(MeetingNumber meetingNumber, AttendanceRegistrationRequest registrationRequest) {
        attendanceRecordService.edit(meetingNumber, registrationRequest);
    }

    public Schedules schedules(MeetingNumber meetingNumber) {
        return scheduleQueryService.schedules(meetingNumber);
    }

    public ParticipantNumberDuplicationStatus participantNumberDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber
    ) {
        return attendanceQueryService.participantNumberDuplicationStatus(meetingNumber, attendanceNumber, participantNumber);
    }

    public ParticipantNameDuplicationStatus participantNameDuplicationStatus(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantName participantName) {

        return attendanceQueryService.participantNameDuplicationStatus(meetingNumber, attendanceNumber, participantName);
    }

    public void delete(AttendanceNumber attendanceNumber) {
        attendanceRecordService.delete(attendanceNumber);
    }
}
