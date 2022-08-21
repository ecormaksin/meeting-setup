package meeting.domain.model.meeting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * メモ
 */
public class Memo {

    static final int MAX_LENGTH = 100;
    static final String WITHIN_MAX_LENGTH = "メモは" + MAX_LENGTH + "文字以内で入力してください。";

    @NotNull(message = WITHIN_MAX_LENGTH)
    @Size(max = MAX_LENGTH, message = WITHIN_MAX_LENGTH)
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
