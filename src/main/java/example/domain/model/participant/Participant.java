package example.domain.model.participant;

import example.domain.model.participant.attendance.Attendances;

/**
 * 参加者
 */
public class Participant {
    ParticipantId participantId;
    ParticipantName participantName;
    Attendances attendances;
    AttendanceComment attendanceComment;

    public String number() {
        return participantId.value();
    }

    public String name() {
        return participantName.value();
    }

    public String attendanceComment() {
        return attendanceComment.value();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Participant{" +
                "participantId=" + participantId +
                ", participantName=" + participantName +
                ", attendances=" + attendances +
                ", attendanceComment=" + attendanceComment +
                '}';
        // @formatter:on
    }
}
