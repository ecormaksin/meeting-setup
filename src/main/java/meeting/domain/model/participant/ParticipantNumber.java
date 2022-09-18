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
    private static final String SPECIFIED_VALUE = "参加者番号は" + SIZE + "桁の半角英数字で指定してください。";

    @NotNull(message = SPECIFIED_VALUE)
    @Pattern(regexp="[\\da-z]{" + SIZE + "}", message = SPECIFIED_VALUE)
    String value;

    @Deprecated
    ParticipantNumber() {}

    public ParticipantNumber(final String value) {
        this.value = value;
    }

    public static ParticipantNumber newNumber() {
        ULID ulid = new ULID();
        return new ParticipantNumber(ulid.nextULID().toLowerCase());
    }

    public static ParticipantNumber of(final String value) {
        return new ParticipantNumber(value);
    }

    public boolean isSame(final ParticipantNumber other) {
        return this.value.equals(other.value);
    }

    public boolean isDifferent(final ParticipantNumber other) {
        return !isSame(other);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
