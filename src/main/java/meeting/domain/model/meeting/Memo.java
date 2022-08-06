package meeting.domain.model.meeting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * メモ
 */
public class Memo {

    static final int MAX_SIZE = 100;
    static final String VALIDATION_ERROR_MESSAGE = "メモは" + MAX_SIZE + "文字以内で入力してください。";

    @NotNull(message = VALIDATION_ERROR_MESSAGE)
    @Size(max = MAX_SIZE, message = VALIDATION_ERROR_MESSAGE)
    String value;

    @Deprecated
    Memo() {}

    public Memo(final String value) {
        this.value = value;
    }

    public static Memo blank() {
        return new Memo("");
    }

    public static Memo of(final String value) {
        return new Memo(value);
    }

    public boolean isBlank() {
        return value.isBlank();
    }

    public String value() {
        return value;
    }
}
