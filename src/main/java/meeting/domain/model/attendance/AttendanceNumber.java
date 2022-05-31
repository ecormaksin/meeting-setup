package meeting.domain.model.attendance;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 出欠番号
 */
public class AttendanceNumber {

    static final int SIZE = 26;
    private static final String MESSAGE = "出欠番号は" + SIZE + "桁の半角英数字で指定してください。";

    @NotNull(message = MESSAGE)
    @Pattern(regexp="[\\da-z]{" + SIZE + "}", message = MESSAGE)
    String value;

    @Deprecated
    AttendanceNumber() {}

    public static AttendanceNumber newNumber() {
        ULID ulid = new ULID();
        return new AttendanceNumber(ulid.nextULID().toLowerCase());
    }

    public AttendanceNumber(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
