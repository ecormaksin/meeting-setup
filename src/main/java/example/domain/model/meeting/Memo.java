package example.domain.model.meeting;

import javax.validation.constraints.NotBlank;

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

    @Deprecated
    Memo() {
    }
}
