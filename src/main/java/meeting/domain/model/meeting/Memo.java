package meeting.domain.model.meeting;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * メモ
 */
public class Memo {

    static final int MAX_SIZE = 100;

    @Size(max = MAX_SIZE, message = "メモは" + MAX_SIZE + "文字以内で入力してください。")
    String value;

    @Deprecated
    Memo() {}

    public Memo(final String value) {
        this.value = value;
    }

    public boolean isBlank() {
        if (Objects.isNull(value)) return true;
        if (value.isBlank()) return true;
        return false;
    }

    public String value() {
        return value;
    }
}
