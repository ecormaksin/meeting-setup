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

    public Memo(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public MemoInputStatus inputStatus() {
        if (Objects.isNull(value)) return MemoInputStatus.なし;
        if (value.isBlank()) return MemoInputStatus.なし;
        return MemoInputStatus.あり;
    }

    @Deprecated
    Memo() {
    }
}
