package example.domain.model.participant;

/**
 * 参加者番号
 */
public class ParticipantNumber {

    String value;

    public ParticipantNumber(String value) {
        this.value = value;
    }

    @Deprecated(since = "MyBatis")
    public ParticipantNumber() {
    }

    @Override
    public String toString() {
        return value;
    }
}
