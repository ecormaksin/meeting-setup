package meeting.domain.model.attendance;

import javax.validation.constraints.NotBlank;

/**
 * 参加者名
 */
public class ParticipantName {
    @NotBlank(message = "{meeting.domain.model.attendance.ParticipantName.NotBlank.message}")
    String value;

    public ParticipantName(String value) {
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
    ParticipantName() {
    }
}
