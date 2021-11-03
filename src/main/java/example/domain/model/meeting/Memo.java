package example.domain.model.meeting;

import javax.validation.constraints.NotBlank;
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

    public boolean isBlank() {
        return Objects.isNull(value) || value.isBlank();
    }

    public boolean isNotBlank() {
        return Objects.nonNull(value) && !value.isBlank();
    }

    @Deprecated
    Memo() {
    }
}
