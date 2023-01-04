package meeting.domain.model.attendance;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 出欠番号
 */
public class AttendanceNumber {

    static final int SIZE = 26;
    static final String SPECIFIED_VALUE = "出欠番号は" + SIZE + "桁の半角英数字で指定してください。";

    public static final String REGEX_PATTERN = "[\\da-z]{" + SIZE + "}";

    @NotNull(message = SPECIFIED_VALUE)
    @Pattern(regexp = REGEX_PATTERN, message = SPECIFIED_VALUE)
    String value;

    @Deprecated
    AttendanceNumber() {
    }

    public static AttendanceNumber newNumber() {
        ULID ulid = new ULID();
        return new AttendanceNumber(ulid.nextULID().toLowerCase());
    }

    public AttendanceNumber(String value) {
        this.value = value;
    }

    public static AttendanceNumber of(String value) {
        return new AttendanceNumber(value);
    }

    public boolean isSame(AttendanceNumber other) {
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
