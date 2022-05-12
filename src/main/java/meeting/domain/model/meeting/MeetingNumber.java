package meeting.domain.model.meeting;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * ミーティング番号
 */
public class MeetingNumber {

    static final int SIZE = 26;
    private static final String MESSAGE = "ミーティング番号は" + SIZE + "桁の半角英数字で指定してください。";

    @NotNull(message = MESSAGE)
    @Pattern(regexp="[0-9a-z]{" + SIZE + "}", message = MESSAGE)
    String value;

    public static MeetingNumber newNumber() {
        ULID ulid = new ULID();
        return new MeetingNumber(ulid.nextULID().toLowerCase());
    }

    public MeetingNumber(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Deprecated
    MeetingNumber() {}
}
