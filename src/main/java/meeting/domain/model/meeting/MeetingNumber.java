package meeting.domain.model.meeting;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * ミーティング番号
 */
public class MeetingNumber {

    static final int SIZE = 26;
    static final String SPECIFIED_VALUE = "ミーティング番号は" + SIZE + "桁の半角英数字で指定してください。";

    public static final String REGEX_PATTERN = "[\\da-z]{" + SIZE + "}";

    @NotNull(message = SPECIFIED_VALUE)
    @Pattern(regexp = REGEX_PATTERN, message = SPECIFIED_VALUE)
    String value;

    @Deprecated
    MeetingNumber() {
    }

    public MeetingNumber(String value) {
        this.value = value;
    }

    public static MeetingNumber newNumber() {
        ULID ulid = new ULID();
        return new MeetingNumber(ulid.nextULID().toLowerCase());
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
