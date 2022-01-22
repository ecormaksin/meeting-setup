package meeting.domain.model.meeting;

import java.util.Objects;

/**
 * メモ
 */
public class Memo {
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
