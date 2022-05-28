package meeting.domain.model.attendance;

import javax.validation.constraints.NotBlank;

/**
 * 参加者名
 */
public class ParticipantName {
    @NotBlank(message = "参加者名を入力してください。")
    String value;

    @Deprecated
    ParticipantName() {}

    public ParticipantName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
