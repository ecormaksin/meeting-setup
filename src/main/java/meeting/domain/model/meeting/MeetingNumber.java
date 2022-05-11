package meeting.domain.model.meeting;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * ミーティング番号
 */
public class MeetingNumber {

    private static final int SIZE = 32;

    @NotBlank(message = "ミーティング番号は32桁の英数字で指定してください。")
    @Pattern(regexp="[0-9a-z]{32}", message = "ミーティング番号は32桁の英数字で指定してください。")
    String value;

    public static MeetingNumber newNumber() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        return new MeetingNumber(uuidString.replace("-", ""));
    }

    public MeetingNumber(String value) {
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
    MeetingNumber() {}
}
