package meeting.domain.model.meeting;

import javax.validation.constraints.AssertTrue;

/**
 * 件名
 */
public class Subject {

    static final int MAX_LENGTH = 30;
    static final String WITHIN_MAX_LENGTH = "件名は" + MAX_LENGTH + "文字以内で必ず入力してください。";

    String value;

    boolean valid;

    @Deprecated
    Subject() {}

    public Subject(String value) {
        this.value = value;

        valid = isValid();
    }

    public static Subject blank() {
        return new Subject("");
    }

    public static Subject of(String value) {
        return new Subject(value);
    }

    @AssertTrue(message = WITHIN_MAX_LENGTH)
    boolean isValid() {
        if ( isBlank() ) return false;
        if ( isLengthOverMaxSize() ) return false;
        return true;
    }

    private boolean isBlank() {
        String whiteSpaceRemoved = value.replaceAll("(　|\\s)+", "");
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
