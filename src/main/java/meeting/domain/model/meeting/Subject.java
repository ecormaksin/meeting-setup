package meeting.domain.model.meeting;

import javax.validation.constraints.AssertTrue;

/**
 * 件名
 */
public class Subject {

    static final int MAX_SIZE = 30;
    private static final String MESSAGE = "件名は" + MAX_SIZE + "文字以内で必ず入力してください。";

    String value;

    @AssertTrue(message = MESSAGE)
    boolean valid;

    @Deprecated
    Subject() {}

    public Subject(final String value) {
        this.value = value;

        validate();
    }

    public static Subject blank() {
        return new Subject("");
    }

    public static Subject of(final String value) {
        return new Subject(value);
    }

    void validate() {
        final String whiteSpaceRemoved = value.replaceAll("(　|\\s)+", "");
        if ( whiteSpaceRemoved.isEmpty() ) {
            valid = false;
            return;
        }

        if ( value.length() > MAX_SIZE ) {
            valid = false;
            return;
        }

        valid = true;
    }

    public String value() {
        return value;
    }
}
