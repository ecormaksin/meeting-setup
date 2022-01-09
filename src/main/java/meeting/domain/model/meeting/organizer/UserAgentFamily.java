package meeting.domain.model.meeting.organizer;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * ユーザー エージェント ファミリー
 */
public class UserAgentFamily {
    @NotBlank(message = "{meeting.domain.model.meeting.organizer.UserAgentFamily.NotBlank.message}")
    String value;

    public UserAgentFamily(String value) {
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
    UserAgentFamily() {
    }

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof UserAgentFamily other)) return false;
        if (Objects.isNull(other.value)) return false;
        return other.value.equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
