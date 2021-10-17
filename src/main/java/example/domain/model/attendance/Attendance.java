package example.domain.model.attendance;

import example.domain.model.participant.ParticipantNumber;

/**
 * 出欠
 */
public class Attendance {
    ParticipantNumber 参加者番号;
    AttendanceCategory 出欠種別;

    public String participantNumber() {
        return 参加者番号.toString();
    }

    public String attendanceCategorySymbol() {
        return 出欠種別.記号();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Attendance{" +
                "参加者番号=" + 参加者番号 +
                ", 出欠種別=" + 出欠種別 +
                '}';
        // @formatter:on
    }
}
