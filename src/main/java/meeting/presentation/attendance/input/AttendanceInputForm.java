package meeting.presentation.attendance.input;

import meeting.domain.model.attendance.AttendanceComment;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.AttendanceRegistrationRequest;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.presentation.meeting.input.schedule.ScheduleInput;
import meeting.presentation.meeting.input.schedule.ScheduleInputs;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 出欠入力フォーム
 */
public class AttendanceInputForm {

    @Valid
    ParticipantName participantName;

    @Valid
    Conveniences conveniences;

    @Valid
    AttendanceComment attendanceComment;

    ScheduleInputs scheduleInputs;

    public AttendanceInputForm(
            @Valid ParticipantName participantName,
            @Valid Conveniences conveniences,
            @Valid AttendanceComment attendanceComment,
            ScheduleInputs scheduleInputs) {

        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
        this.scheduleInputs = scheduleInputs;
    }

    @Deprecated
    AttendanceInputForm() {
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

    public ScheduleInputs scheduleInputs() {
        return scheduleInputs;
    }

    public static AttendanceInputForm empty(
            ParticipantName participantName,
            ScheduleInputs schedules) {

        List<Convenience> convenienceList = new ArrayList<>();

        for (ScheduleInput schedule : schedules.asList()) {
            Convenience convenience = new Convenience(schedule.scheduleNumber(), ConvenienceCategory.BAD);
            convenienceList.add(convenience);
        }

        return new AttendanceInputForm(
                participantName,
                Conveniences.of(convenienceList),
                AttendanceComment.empty(),
                schedules
        );
    }

    public AttendanceRegistrationRequest toRegistrationRequest(AttendanceNumber attendanceNumber, ParticipantNumber participantNumber) {

        return new AttendanceRegistrationRequest(
                attendanceNumber,
                participantNumber,
                participantName,
                conveniences,
                attendanceComment);
    }
}
