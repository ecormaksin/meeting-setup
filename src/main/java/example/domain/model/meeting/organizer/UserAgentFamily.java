package example.domain.model.meeting.organizer;

import javax.validation.constraints.NotBlank;

/**
 * 主催者端末のユーザー エージェント ファミリー
 */
public class UserAgentFamily {
    @NotBlank(message = "{example.domain.model.meeting.organizer.UserAgentFamily.NotBlank.message}")
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
}
