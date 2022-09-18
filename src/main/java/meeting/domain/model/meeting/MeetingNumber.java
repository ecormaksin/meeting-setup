package meeting.domain.model.meeting;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * ミーティング番号
 */
public class MeetingNumber {

    static final int SIZE = 26;
    public static final String REGEXP = "[\\da-z]{" + SIZE + "}";
    private static final String SPECIFIED_VALUE = "ミーティング番号は" + SIZE + "桁の半角英数字で指定してください。";

    @NotNull(message = SPECIFIED_VALUE)
    @Pattern(regexp=REGEXP, message = SPECIFIED_VALUE)
    String value;

    @Deprecated
    MeetingNumber() {}

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
