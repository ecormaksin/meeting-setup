package meeting.domain.model.meeting.organizer;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * OSファミリー
 */
public class OSFamily {
    @NotBlank(message = "{meeting.domain.model.meeting.organizer.OSFamily.NotBlank.message}")
    String value;

    public OSFamily(String value) {
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
    OSFamily() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof OSFamily other)) return false;
        if (Objects.isNull(other.value)) return false;
        return other.value.equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
