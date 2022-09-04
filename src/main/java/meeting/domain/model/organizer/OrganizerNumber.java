package meeting.domain.model.organizer;

import de.huxhorn.sulky.ulid.ULID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 主催者番号
 */
public class OrganizerNumber {

    static final int SIZE = 26;
    public static final String REGEXP = "[\\da-z]{" + SIZE + "}";
    private static final String SPECIFIED_VALUE = "主催者番号は" + SIZE + "桁の半角英数字で指定してください。";

    @NotNull(message = SPECIFIED_VALUE)
    @Pattern(regexp="[\\da-z]{" + SIZE + "}", message = SPECIFIED_VALUE)
    String value;

    @Deprecated
    OrganizerNumber() {}

    public OrganizerNumber(final String value) {
        this.value = value;
    }

    public static OrganizerNumber newNumber() {
        ULID ulid = new ULID();
        return new OrganizerNumber(ulid.nextULID().toLowerCase());
    }

    public static OrganizerNumber of(final String value) {
        return new OrganizerNumber(value);
    }

    public boolean isSame(final OrganizerNumber other) {
        return this.value.equals(other.value);
    }

    public boolean isDifferent(final OrganizerNumber other) {
        return !isSame(other);
    }

    public String value() {
        return value;
    }
}
