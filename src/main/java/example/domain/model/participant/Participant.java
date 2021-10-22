package example.domain.model.participant;

import example.domain.model.participant.attendance.Attendances;

/**
 * 参加者
 */
public class Participant {
    ParticipantNumber 参加者番号;
    String 名前;
    Attendances 出欠;
    String コメント;

    public String number() {
        return 参加者番号.toString();
    }

    public String name() {
        return 名前;
    }

    public String comment() {
        return コメント;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Participant{" +
                "参加者番号=" + 参加者番号 +
                ", 名前='" + 名前 + '\'' +
                ", 出欠=" + 出欠 +
                ", コメント='" + コメント + '\'' +
                '}';
        // @formatter:on
    }
}
