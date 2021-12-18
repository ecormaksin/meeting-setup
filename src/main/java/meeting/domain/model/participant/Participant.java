package meeting.domain.model.participant;

import meeting.domain.model.participant.attendance.Attendances;

/**
 * 参加者
 */
public class Participant {
    ParticipantId participantId;
    ParticipantName participantName;
    Attendances attendances;
    AttendanceComment attendanceComment;

    public Participant(ParticipantId participantId,
                       ParticipantName participantName,
                       Attendances attendances,
                       AttendanceComment attendanceComment) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.attendances = attendances;
        this.attendanceComment = attendanceComment;
    }

    @Deprecated
    public Participant() {}

    public Attendances attendances() {
        return attendances;
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
