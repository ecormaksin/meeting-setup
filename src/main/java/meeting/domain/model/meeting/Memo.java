package meeting.domain.model.meeting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * メモ
 */
public class Memo {

    static final int MAX_SIZE = 100;

    @NotNull
    @Size(max = MAX_SIZE, message = "メモは" + MAX_SIZE + "文字以内で入力してください。")
    String value;

    @Deprecated
    Memo() {}

    public Memo(@NotNull final String value) {
        this.value = value;
    }

    public static Memo blank() {
        return new Memo("");
    }

    public boolean isBlank() {
        return value.isBlank();
    }

    public String value() {
        return value;
    }
}
