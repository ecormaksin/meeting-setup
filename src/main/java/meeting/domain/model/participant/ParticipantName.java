package meeting.domain.model.participant;

import javax.validation.constraints.AssertTrue;

/**
 * 参加者名
 */
public class ParticipantName {
    static final int MAX_LENGTH = 10;
    private static final String WITHIN_MAX_LENGTH = "参加者名は" + MAX_LENGTH + "文字以内で必ず入力してください。";

    String value;

    boolean valid;

    @Deprecated
    ParticipantName() {}

    public ParticipantName(final String value) {
        this.value = value;

        valid = isValid();
    }

    public static ParticipantName blank() {
        return new ParticipantName("");
    }

    public static ParticipantName of(final String value) {
        return new ParticipantName(value);
    }

    @AssertTrue(message = WITHIN_MAX_LENGTH)
    boolean isValid() {
        if ( isBlank() ) return false;
        if ( isLengthOverMaxSize() ) return false;
        return true;
    }

    private boolean isBlank() {
        final String whiteSpaceRemoved = value.replaceAll("(　|\\s)+", "");
        return whiteSpaceRemoved.isBlank();
    }

    private boolean isLengthOverMaxSize() {
        return value.length() > MAX_LENGTH;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
