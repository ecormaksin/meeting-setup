package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * 出欠編集依頼
 */
public class AttendanceEditRequest {

    @Valid
    AttendanceNumber attendanceNumber;

    @Valid
    ParticipantName participantName;

    @Valid
    Conveniences conveniences;

    @Valid
    AttendanceComment attendanceComment;

    Schedules schedules;

    public AttendanceEditRequest(@Valid AttendanceNumber attendanceNumber,
                                 @Valid ParticipantName participantName,
                                 @Valid Conveniences conveniences,
                                 @Valid AttendanceComment attendanceComment,
                                 Schedules schedules) {
        this.attendanceNumber = attendanceNumber;
        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
        this.schedules = schedules;
    }

    @Deprecated
    AttendanceEditRequest() {}

    public AttendanceNumber attendanceId() {
        return attendanceNumber;
    }

    public ParticipantName participantName() {
        return participantName;
    }

    public Conveniences conveniences() {
        return conveniences;
    }

    public AttendanceComment attendanceComment() {
        return attendanceComment;
    }

    public Schedules schedules() {
        return schedules;
    }
}
