package example.domain.model.participant;

/**
 * 参加者
 */
public class Participant {
    ParticipantNumber 参加者番号;
    String 名前;
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
                "参加者番号='" + 参加者番号 + '\'' +
                ", 名前='" + 名前 + '\'' +
                ", コメント='" + コメント + '\'' +
                '}';
        // @formatter:on
    }
}
