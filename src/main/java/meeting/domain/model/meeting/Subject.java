package meeting.domain.model.meeting;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 件名
 */
public class Subject {

    static final int MAX_SIZE = 30;
    private static final String MESSAGE = "件名は" + MAX_SIZE + "文字以内で必ず入力してください。";

    String value;

    @Deprecated
    Subject() {}

    public Subject(final String value) {
        this.value = value;
    }

    public static Subject empty() {
        return new Subject("");
    }

    @AssertTrue(message = MESSAGE)
    boolean isValid() {
        if ( Objects.isNull(value) ) return false;

        final String whiteSpaceRemoved = value.replaceAll("(　|\\s)+", "");
        if ( whiteSpaceRemoved.isEmpty() ) return false;

        if ( value.length() > MAX_SIZE ) return false;

        return true;
    }

    public String value() {
        return value;
    }
}
