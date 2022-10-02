package meeting.presentation.attendance.input;

import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;

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

    Schedules schedules;

    public AttendanceInputForm(
            @Valid ParticipantName participantName,
            @Valid Conveniences conveniences,
            @Valid AttendanceComment attendanceComment,
            Schedules schedules) {

        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
        this.schedules = schedules;
    }

    @Deprecated
    AttendanceInputForm() {}

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

    public static AttendanceInputForm empty(
            ParticipantName participantName,
            Schedules schedules) {

        List<Convenience> convenienceList = new ArrayList<>();

        for (Schedule schedule : schedules.asList()) {
            Convenience convenience = new Convenience(schedule.scheduleNumber(), ConvenienceCategory.バツ);
            convenienceList.add(convenience);
        }

        AttendanceInputForm inputForm = new AttendanceInputForm();

        inputForm.participantName = participantName;
        inputForm.conveniences = new Conveniences(convenienceList);
        inputForm.schedules = schedules;

        return inputForm;
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
