package meeting.domain.model.participant;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 参加者番号
 */
public class ParticipantNumber {

    static final int SIZE = 26;
    public static final String REGEXP = "[\\da-z]{" + SIZE + "}";
    static final String SPECIFIED_VALUE = "参加者番号は" + SIZE + "桁の半角英数字で指定してください。";

    @NotNull(message = SPECIFIED_VALUE)
    @Pattern(regexp="[\\da-z]{" + SIZE + "}", message = SPECIFIED_VALUE)
    String value;

    @Deprecated
    ParticipantNumber() {}

    public ParticipantNumber(String value) {
        this.value = value;
    }

    public static ParticipantNumber newNumber() {
        ULID ulid = new ULID();
        return new ParticipantNumber(ulid.nextULID().toLowerCase());
    }

    public static ParticipantNumber of(String value) {
        return new ParticipantNumber(value);
    }

    public boolean isSame(ParticipantNumber other) {
        return this.value.equals(other.value);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
