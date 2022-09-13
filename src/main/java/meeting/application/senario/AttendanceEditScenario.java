package meeting.application.senario;

import meeting.application.service.attendance.AttendanceQueryService;
import meeting.application.service.attendance.AttendanceRecordService;
import meeting.application.service.schedule.ScheduleQueryService;
import meeting.domain.model.attendance.*;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
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

    public Attendance attendanceOf(AttendanceNumber attendanceNumber) {
        return attendanceQueryService.attendanceOf(attendanceNumber);
    }

    public void edit(MeetingNumber meetingNumber, AttendanceEditRequest attendanceEdittRequest) {
        attendanceRecordService.edit(meetingNumber, attendanceEdittRequest);
    }

    public Schedules schedules(MeetingNumber meetingNumber) {
        return scheduleQueryService.schedules(meetingNumber);
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
