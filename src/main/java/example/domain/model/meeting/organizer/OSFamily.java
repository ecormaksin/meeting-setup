package example.domain.model.meeting.organizer;

import javax.validation.constraints.NotBlank;

/**
 * 主催者端末のOSファミリー
 */
public class OSFamily {
    @NotBlank(message = "{example.domain.model.meeting.organizer.OSFamily.NotBlank.message}")
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
}
