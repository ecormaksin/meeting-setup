package meeting.domain.model.attendance;

import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 出欠入力依頼
 */
public class AttendanceInputRequest {

    @Valid
    ParticipantName participantName;

    @Valid
    Conveniences conveniences;

    @Valid
    AttendanceComment attendanceComment;

    public AttendanceInputRequest(@Valid ParticipantName participantName,
                                  @Valid Conveniences conveniences,
                                  @Valid AttendanceComment attendanceComment) {
        this.participantName = participantName;
        this.conveniences = conveniences;
        this.attendanceComment = attendanceComment;
    }

    @Deprecated
    AttendanceInputRequest() {}

    public ParticipantName participantName() {
        return participantName;
    }

    public Conveniences conveniences() {
        return conveniences;
    }

    public AttendanceComment attendanceComment() {
        return attendanceComment;
    }

    public static AttendanceInputRequest empty(Schedules schedules) {

        List<Convenience> convenienceList = new ArrayList<>();

        for (Schedule schedule : schedules.asList()) {
            Convenience convenience = new Convenience(schedule.scheduleNumber(), ConvenienceCategory.バツ);
            convenienceList.add(convenience);
        }

        AttendanceInputRequest attendanceInputRequest = new AttendanceInputRequest();
        attendanceInputRequest.conveniences = new Conveniences(convenienceList);
        return attendanceInputRequest;
    }
}
